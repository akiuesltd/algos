package com.akieus.stst.collections;

public class Utils {
    public static boolean less(double i, double j) {
        return i < j;
    }

    public static void exch(double[] arr, int i, int j) {
        double swap = arr[i];
        arr[i] = arr[j];
        arr[j] = swap;
    }
}
