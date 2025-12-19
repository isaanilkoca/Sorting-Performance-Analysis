public class Main {
    public static void main(String[] args) throws Exception {

        int[] sizes = { 1000, 5000, 10000, 20000 };
        int trials = 5;
        long seed = 42L;

        BenchmarkRunner bench = new BenchmarkRunner(sizes, trials, seed);
        bench.run();

        String rawCsv = "results/raw_results.csv";
        String aggCsv = "results/aggregated_results.csv";

        bench.writeCsv(rawCsv, aggCsv);

        System.out.println("CSV files written to: " + rawCsv + " and " + aggCsv);
    }
}
