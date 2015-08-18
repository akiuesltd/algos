import java.util.Arrays;

/**
 * @author aks
 * @since 17/08/15
 */
public class InsertionSort extends Sort {
    public InsertionSort() {
    }

    public static void main(String[] args) {
        int[] arr = new int[]{7, 6, 1, 2, 5, 9, 4, 3};
        new InsertionSort().sortIterative(arr);
        System.out.println(Arrays.toString(arr));
    }

    public void sortIterative(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int j = i;
            while (j > 0 && a[j] > a[j - 1]) {
                exch(a, j, --j);
            }
        }
    }
}
