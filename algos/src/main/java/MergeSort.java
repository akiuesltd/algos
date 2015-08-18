import java.util.Arrays;

/**
 * Divide-and-concur.
 * - Recursively divide an array into two parts,
 * - sort each part (recursively),
 * - and them merge them using an auxiliary array.
 *
 * Optimisations
 *  - During merge don't continue copying if remaining elements are already sorted
 *  - Use Insertion sort for array size < 7
 *  - Challenge - avoid copy operations by doing merge into aux array.
 * @author aks
 * @since 17/08/15
 */
public class MergeSort extends Sort {
    public MergeSort() {
    }

    public static void main(String[] args) {
        int[] arr = new int[]{7, 6, 1, 2, 5, 9, 4, 3};
//        int[] arr = new int[]{3, 2, 1};
        Sort sort = new MergeSort();
        sort.sort(arr);
        System.out.println(Arrays.toString(arr));
        System.out.println("checkCount=" + sort.checkCount);
        System.out.println("exchCount=" + sort.exchCount);
    }

    public void sort(int[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    private void sort(int[] arr, int from, int to) {
        int length = to - from + 1;
        if (length == 2) {
            merge(arr, from, from, to);
        } else if (length > 2) {
            int mid = (to + from) / 2;
            sort(arr, from, mid);
            sort(arr, mid + 1, to);
            merge(arr, from, mid, to);
        }
    }

    private void merge(int[] arr, int from, int mid, int to) {
        int k = from, i = from, j = mid + 1;

        int[] copy = copy(arr, from, to);
        while (i <= mid && j <= to) {
            if (less(copy[j], copy[i])) {
                arr[k++] = copy[j++];
            } else {
                arr[k++] = copy[i++];
            }
        }
        while (i <= mid) {
            arr[k++] = copy[i++];
        }
        while (j <= from) {
            arr[k++] = copy[j++];
        }
    }

    private int[] copy(int[] a, int from, int to) {
        return Arrays.copyOf(a, a.length);
    }
}
