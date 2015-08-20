package com.akieus.algos.coursera.sort;

/**
 * Problem: Given an array on N items, find the k'th smallest one
 * <p>
 * Similar to QuickSort.
 * 1) Find your partition point.
 * 2) If partition point is = k, you got your match.
 * 3) Else search in left or right array depending on value of k vs partition point.
 *
 * @author aks
 * @since 19/08/15
 */
public class QuickSelection {
    public QuickSelection() {
    }

    public static void main(String[] args) {
        int[] arr = new int[]{7, 6, 1, 2, 5, 9, 4, 3};
        int k = 0;
//        int[] arr = new int[]{1, 2, 4, 3};
        QuickSelection sort = new QuickSelection();
        System.out.println(0 + "th: " + sort.find(arr, 0));
        System.out.println(5 + "th: " + sort.find(arr, 5));
        System.out.println(7 + "th: " + sort.find(arr, 7));

    }

    public int find(int[] arr, int k) {
        if (k >= arr.length) {
            throw new IllegalArgumentException();
        }

        new Shuffle().sort(arr);
        int start = 0, end = arr.length - 1;
        while (start < end) {
            int partition = partition(arr, start, end);
            if (k > partition) {
                start = partition + 1;
            } else if (k < partition) {
                end = partition - 1;
            } else {
                return arr[partition];
            }

        }
        return arr[k];
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

    public boolean less(int i, int j) {
        return i < j;
    }

    public void exch(int[] arr, int i, int j) {
        int swap = arr[i];
        arr[i] = arr[j];
        arr[j] = swap;
    }
}
