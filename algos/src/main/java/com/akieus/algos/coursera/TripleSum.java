package com.akieus.algos.coursera;

/**
 * Given N distinct integers, how many triples sum to exactly zero.
 *
 * @author aks
 * @since 14/08/15
 */
public class TripleSum {
    public static void main(String[] args) {
        int[] a = testData();
        int n = a.length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (a[i] + a[j] + a[k] == 0) {
                        count++;
                    }
                }
            }
        }
        System.out.println(count);
    }

    private static int[] testData() {
        return new int[]{30, -40, -20, -10, 40, 0, 10, 5};
    }
}
