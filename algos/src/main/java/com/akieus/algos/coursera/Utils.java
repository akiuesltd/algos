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

    public static <Key> Key[] resizeIfRequired(Key[] arr, int size) {
        if (size < arr.length) {
            return arr;
        }
        Key[] copy = (Key[]) (new Comparable[size * 2]);
        for (int i = 0; i < size; i++) {
            copy[i] = arr[i];
        }
        return copy;
    }

    public static int[] resizeIfRequired(int[] arr, int size) {
        if (size < arr.length) {
            return arr;
        }
        int[] copy = new int[arr.length * 2];
        for (int i = 0; i < arr.length; i++) {
            copy[i] = arr[i];
        }
        return copy;
    }

    public static int[] shrinkIfCan(int[] arr, int size) {
        if (size > arr.length / 4) {
            return arr;
        }
        int[] copy = new int[size * 2];
        for (int i = 0; i < size; i++) {
            copy[i] = arr[i];
        }
        return copy;
    }

    public static <Key> Key[] shrinkIfCan(Key[] arr, int size) {
        if (size > arr.length / 4) {
            return arr;
        }
        Key[] copy = (Key[]) new Comparable[size * 2];
        for (int i = 0; i < size; i++) {
            copy[i] = arr[i];
        }
        return copy;
    }
}
