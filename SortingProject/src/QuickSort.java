class QuickSort implements SortAlgorithm {

    @Override
    public void sort(int[] a) {
        if (a == null || a.length < 2) return;
        quickSort(a, 0, a.length - 1);
    }

    private void quickSort(int[] a, int left, int right) {
        // Iterative-style recursion on smaller side to avoid deep stack
        while (left < right) {
            int pivotIndex = partitionMedianOfThree(a, left, right);

            // sort smaller side recursively, larger side by looping
            if (pivotIndex - left < right - pivotIndex) {
                quickSort(a, left, pivotIndex - 1);
                left = pivotIndex + 1;
            } else {
                quickSort(a, pivotIndex + 1, right);
                right = pivotIndex - 1;
            }
        }
    }

    private int partitionMedianOfThree(int[] a, int left, int right) {
        int mid = left + (right - left) / 2;
        int pivotIndex = medianIndex(a, left, mid, right);

        swap(a, pivotIndex, right);
        int pivot = a[right];

        int i = left;
        for (int j = left; j < right; j++) {
            if (a[j] <= pivot) {
                swap(a, i, j);
                i++;
            }
        }
        swap(a, i, right);
        return i;
    }

    private int medianIndex(int[] a, int i, int j, int k) {
        int ai = a[i], aj = a[j], ak = a[k];

        if ((ai <= aj && aj <= ak) || (ak <= aj && aj <= ai)) return j;
        if ((aj <= ai && ai <= ak) || (ak <= ai && ai <= aj)) return i;
        return k;
    }

    private void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    @Override
    public String name() {
        return "QuickSort";
    }
}
