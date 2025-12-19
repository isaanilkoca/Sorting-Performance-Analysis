import java.util.Random;

class DataGenerator {

    public static int[] randomArray(int n, long seed) {
        int[] arr = new int[n];
        Random rnd = new Random(seed);
        for (int i = 0; i < n; i++) {
            arr[i] = rnd.nextInt();
        }
        return arr;
    }

    public static int[] sortedArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = i;
        return arr;
    }

    public static int[] reversedArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = n - i;
        return arr;
    }
}
