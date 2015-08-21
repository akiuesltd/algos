package com.akieus.algos.coursera;

/**
 * @author aks
 * @since 21/08/15
 */
public class Utils {
    public static <T extends Comparable<T>> boolean less(T i, T j) {
        return i.compareTo(j) < 0;
    }

    public static boolean less(int i, int j) {
        return i < j;
    }

    public static void exch(int[] arr, int i, int j) {
        int swap = arr[i];
        arr[i] = arr[j];
        arr[j] = swap;
    }

    public static <T> void exch(T[] arr, int i, int j) {
        T swap = arr[i];
        arr[i] = arr[j];
        arr[j] = swap;
    }

}
