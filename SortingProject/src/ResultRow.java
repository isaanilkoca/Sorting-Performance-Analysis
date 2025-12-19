public class ResultRow {
    public final String algorithm;
    public final String dataset;
    public final int n;
    public final int trials;
    public final long avgNano;

    public ResultRow(String algorithm, String dataset, int n, int trials, long avgNano) {
        this.algorithm = algorithm;
        this.dataset = dataset;
        this.n = n;
        this.trials = trials;
        this.avgNano = avgNano;
    }

    @Override
    public String toString() {
        return algorithm + " | " + dataset + " | n=" + n + " | avg=" + avgNano;
    }
}
