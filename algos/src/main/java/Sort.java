/**
 * @author aks
 * @since 17/08/15
 */
public abstract class Sort {
    protected int checkCount=0;
    protected int exchCount=0;

    public abstract void sortIterative(int[] a);

    public <T extends Comparable<T>> boolean less(T i, T j) {
        checkCount++;
        return i.compareTo(j) < 0;
    }

    public boolean less(int i, int j) {
        checkCount++;
        return i < j;
    }

    public void exch(int[] arr, int i, int j) {
        int swap = arr[i];
        arr[i] = arr[j];
        arr[j] = swap;
        exchCount++;
    }

    public <T> void exch(T[] arr, int i, int j) {
        T swap = arr[i];
        arr[i] = arr[j];
        arr[j] = swap;
        exchCount++;
    }
}
