class MergeSort implements SortAlgorithm {

    @Override
    public void sort(int[] a) {
        if (a == null || a.length < 2) return;
        int[] aux = new int[a.length];
        mergeSort(a, aux, 0, a.length - 1);
    }

    private void mergeSort(int[] a, int[] aux, int left, int right) {
        if (left >= right) return;

        int mid = left + (right - left) / 2;

        mergeSort(a, aux, left, mid);
        mergeSort(a, aux, mid + 1, right);

        if (a[mid] <= a[mid + 1]) return;  // already sorted small optimization

        merge(a, aux, left, mid, right);
    }

    private void merge(int[] a, int[] aux, int left, int mid, int right) {
        System.arraycopy(a, left, aux, left, right - left + 1);

        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            if (aux[i] <= aux[j]) a[k++] = aux[i++];
            else a[k++] = aux[j++];
        }

        while (i <= mid) a[k++] = aux[i++];
        while (j <= right) a[k++] = aux[j++];
    }

    @Override
    public String name() {
        return "MergeSort";
    }
}
