import java.util.Arrays;

/**
 * 1) Must shuffle the array to avoid worst case performance.
 * 2) Find an element k such every element before k is smaller than k and every element after k is larger.
 * 3) recursively sort array before k and the array after k.
 *
 * @author aks
 * @since 17/08/15
 */
public class QuickSort extends Sort {
    public QuickSort() {
    }

    public static void main(String[] args) {
//        int[] arr = new int[]{7, 6, 1, 2, 5, 9, 4, 3};
        int[] arr = new int[]{1, 2, 4, 3};
        Sort sort = new QuickSort();
        sort.sort(arr);
        System.out.println(Arrays.toString(arr));
        System.out.println("checkCount=" + sort.checkCount);
        System.out.println("exchCount=" + sort.exchCount);
    }

    public void sort(int[] arr) {
//        new Shuffle().sort(arr);
        sort(arr, 0, arr.length - 1);
    }

    public void sort(int[] arr, int start, int end) {
        System.out.println("+" + Arrays.toString(arr) + "<< " + start + ", " + end);
        if (start >= end) {
            return;
        }
        int i = start + 1, j = end, k = start;
        while (i < j) {
            if (less(arr[i], arr[k])) {
                i++;
            } else {
                exch(arr, i, j--);
            }
            if (less(arr[j], arr[k])) {
                exch(arr, i++, j);
            } else {
                j--;
            }
        }
        if (less(arr[j], arr[k])) {
            exch(arr, k, j);
        }
        System.out.println("j=" + j);
        sort(arr, start, j - 1);
        sort(arr, j + 1, end);
        System.out.println("-" + Arrays.toString(arr));
    }

}
