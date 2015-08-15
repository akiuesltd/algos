package com.akieus.algos.coursera;

/**
 * Given N distinct integers, how many triples sum to exactly zero.
 * <p>
 * Solve it in N^2 lgN.
 * - Sort the array (N lgN)
 * - For each pair of entries (N^2), binary search if there is an entry in the rest of the array
 *
 * @author aks
 * @since 14/08/15
 */
public class TripleSumDeluxe {
    public static void main(String[] args) {
        int[] a = testData();
        int n = a.length;
        int count = 0;
        // TODO - implement me
        System.out.println(count);
    }

    private static int[] testData() {
        return new int[]{30, -40, -20, -10, 40, 0, 10, 5};
    }
}
