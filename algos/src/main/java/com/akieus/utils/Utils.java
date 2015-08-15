package com.akieus.utils;

/**
 * @author aks
 * @since 13/08/15
 */
public class Utils {
    public static void printArray(int[] arr) {
        for (int i : arr) {
            System.out.print(i + ", ");
        }
        System.out.println("");
    }

    public static <T> void printArray(T[] arr) {
        for (T t : arr) {
            System.out.print(t.toString() + ", ");
        }
        System.out.println("");
    }
}
