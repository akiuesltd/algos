package com.akieus.algos.coursera.sort;

import java.util.Arrays;

/**
 * 1) Must shuffle the array to avoid worst case performance.
 * 2) Find an element k such every element before k is smaller than k and every element after k is larger.
 * 3) recursively sort array before k and the array after k.
 *
 * @author aks
 * @since 17/08/15
 */
public class QuickSortRecursive extends Sort {
    public QuickSortRecursive() {
    }

    public static void main(String[] args) {
        int[] arr = new int[]{7, 6, 1, 2, 5, 9, 4, 3};
//        int[] arr = new int[]{1, 2, 4, 3};
        Sort sort = new QuickSortRecursive();
        sort.sort(arr);
        System.out.println(Arrays.toString(arr));
        System.out.println("checkCount=" + sort.checkCount);
        System.out.println("exchCount=" + sort.exchCount);
    }

    public void sort(int[] arr) {
        new Shuffle().sort(arr);
        System.out.println(Arrays.toString(arr));
        sort(arr, 0, arr.length - 1);
    }

    public void sort(int[] a, int start, int end) {
        if (start >= end) return;
        int j = partition(a, start, end);
        sort(a, start, j - 1);
        sort(a, j + 1, end);
    }

    private int partition(int[] a, int start, int end) {
        int i = start + 1, j = end, k = start;
        while (i <= j) {
            if (less(a[i], a[k])) i++;
            else if (less(a[k], a[j])) j--;
            else exch(a, i++, j--);
        }
        exch(a, k, j);
        return j;
    }
}
