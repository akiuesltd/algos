import java.util.Arrays;

/**
 * to
 *
 * @author aks
 * @since 17/08/15
 */
public class BottomUpMergeSort extends Sort {
    public BottomUpMergeSort() {
    }

    public static void main(String[] args) {
        int[] arr = new int[]{7, 6, 1, 2, 5, 9, 4, 3};
//        int[] arr = new int[]{3, 2, 1};
        Sort sort = new BottomUpMergeSort();
        sort.sort(arr);
        System.out.println(Arrays.toString(arr));
        System.out.println("checkCount=" + sort.checkCount);
        System.out.println("exchCount=" + sort.exchCount);
    }

    public void sort(int[] arr) {
        int bubble = 1;
        int[] aux = new int[arr.length];

        while (bubble < arr.length) {
            int jump = 2 * bubble;
            for (int i = 0; i < arr.length; i = i + jump) {
                int low = i;
                int mid = low + bubble;
                int end = i + 2 * bubble - 1;
                if (end > arr.length - 1) {
                    end = arr.length - 1;
                }

                merge(arr, aux, i, mid, end);
            }
            bubble *= 2;
        }
    }

    public void merge(int[] arr, int[] aux, int low, int mid, int high) {
        for (int i = low; i <= high; i++) {
            aux[i] = arr[i];
        }

        int i = low, j = mid, k = low;
        while (k <= high) {
            if (i >= mid) {
                arr[k++] = aux[j++];
            } else if (j > high) {
                arr[k++] = aux[i++];
            } else if (less(aux[j], aux[i])) {
                arr[k++] = aux[j++];
            } else {
                arr[k++] = aux[i++];
            }
        }
    }

}
