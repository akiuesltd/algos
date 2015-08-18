import java.util.Arrays;

/**
 * @author aks
 * @since 17/08/15
 */
public class ShellSort extends Sort {
    public ShellSort() {
    }

    public static void main(String[] args) {
        int[] arr = new int[]{7, 6, 1, 2, 5, 9, 4, 3};
        Sort sort = new ShellSort();
        sort.sort(arr);
        System.out.println(Arrays.toString(arr));
        System.out.println("checkCount=" + sort.checkCount);
        System.out.println("exchCount=" + sort.exchCount);
    }

    public void sort(int[] a) {
        int h = 1;
        while (h < a.length / 3) {
            h = 3 * h + 1;
        }
        while ((h = h / 3) > 0) {
            for (int i = h; i < a.length; i++) {
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
                    exch(a, j, j - h);
                }
            }
        }
    }
}
