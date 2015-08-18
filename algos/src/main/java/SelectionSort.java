import java.util.Arrays;

/**
 * @author aks
 * @since 17/08/15
 */
public class SelectionSort extends Sort {
    public SelectionSort() {
    }

    public static void main(String[] args) {
        int[] arr = new int[]{7, 6, 1, 2, 5, 9, 4, 3};
        new SelectionSort().sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public void sort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }
            exch(arr, i, min);
        }
    }
}
