import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class BenchmarkRunner {

    private final int[] sizes;
    private final int trials;
    private final long seed;

    private final List<String> rawLines = new ArrayList<>();
    private final List<String> aggregatedLines = new ArrayList<>();

    public BenchmarkRunner(int[] sizes, int trials, long seed) {
        this.sizes = sizes;
        this.trials = trials;
        this.seed = seed;

        rawLines.add("algorithm,dataset,size,trial,nanoseconds");
        aggregatedLines.add("algorithm,dataset,size,trials,avg_nanoseconds");
    }

    public void run() {

        String[] datasets = { "random", "sorted", "reversed" };

        SortAlgorithm[] algorithms = new SortAlgorithm[] {
                new InsertionSort(),
                new MergeSort(),
                new QuickSort()
        };

        System.out.println("=== Benchmark started ===");
        System.out.println("Sizes: " + Arrays.toString(sizes));
        System.out.println("Trials per case: " + trials);
        System.out.println();

        for (String ds : datasets) {
            for (int n : sizes) {
                for (SortAlgorithm algo : algorithms) {

                    long totalNs = 0L;

                    for (int t = 1; t <= trials; t++) {

                        int[] arr = makeInput(ds, n, seed + t);

                        long start = System.nanoTime();
                        algo.sort(arr);
                        long end = System.nanoTime();
                        long duration = end - start;

                        if (!isSorted(arr)) {
                            throw new RuntimeException(
                                    "Algorithm " + algo.name() +
                                            " failed for dataset=" + ds +
                                            ", n=" + n);
                        }

                        totalNs += duration;

                        // raw results: her trial icin satir
                        rawLines.add(
                                algo.name() + "," +
                                        ds + "," +
                                        n + "," +
                                        t + "," +
                                        duration
                        );
                    }

                    long avgNs = totalNs / trials;

                    // aggregated results: her kombinasyon icin tek satir
                    aggregatedLines.add(
                            algo.name() + "," +
                                    ds + "," +
                                    n + "," +
                                    trials + "," +
                                    avgNs
                    );

                    System.out.println(
                            algo.name() +
                                    " | dataset=" + ds +
                                    " | n=" + n +
                                    " | avg(ns)=" + avgNs
                    );
                }
            }
        }

        System.out.println();
        System.out.println("=== Benchmark finished ===");
    }

    public void writeCsv(String rawPath, String aggregatedPath) throws java.io.IOException {
        CsvWriter.write(rawPath, rawLines);
        CsvWriter.write(aggregatedPath, aggregatedLines);
    }

    private int[] makeInput(String dataset, int n, long seed) {
        switch (dataset) {
            case "sorted":
                return DataGenerator.sortedArray(n);
            case "reversed":
                return DataGenerator.reversedArray(n);
            default:
                return DataGenerator.randomArray(n, seed);
        }
    }

    private boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) {
            if (a[i - 1] > a[i]) return false;
        }
        return true;
    }
}
