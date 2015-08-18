import java.util.Arrays;

/**
 * @author aks
 * @since 17/08/15
 */
public class Shuffle extends Sort {
    public Shuffle() {
    }

    public static void main(String[] args) {
        int[] arr = new int[]{7, 6, 1, 2, 5, 9, 4, 3};
        Sort sort = new Shuffle();
        sort.sort(arr);
        System.out.println(Arrays.toString(arr));
        System.out.println("checkCount=" + sort.checkCount);
        System.out.println("exchCount=" + sort.exchCount);
    }

    public void sort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int r = StdRandom.uniform(i);
            exch(a, r, i);
        }
    }
}
