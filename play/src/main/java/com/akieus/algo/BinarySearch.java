package com.akieus.algo;

public class BinarySearch {

    private int[] arr = null;

    public BinarySearch(int n) {
        this.arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }
    }

    /**
     * Binary search for k in the array, return index of the element if found,
     * -1 if not.
     *
     * @param k
     * @return index of k if its found in the array, otherwise -1.
     */
    public int search(int k) {
        return search(0, arr.length - 1, k);
    }

    /**
     * Search for k between arr[i] to arr[j]
     *
     * @param i
     * @param j
     * @return index of k
     */
    private int search(int i, int j, int k) {
        if (i > j) {
            throw new IllegalStateException("i>j");
        }

        if (i == j) {
            return (arr[i] == k) ? i : -1;
        }

        int m = (i + j) / 2;
        if (arr[m] == k) {
            return m;
        } else if (arr[m] < k) {
            return search(m + 1, j, k);
        } else {
            return search(i, m - 1, k);
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            printUsage();
            System.exit(-1);
        }

        int n = Integer.parseInt(args[0]);
        int k = Integer.parseInt(args[1]);

        System.out.println(new BinarySearch(n).search(k));
    }

    private static void printUsage() {
        System.out.println("java " + BinarySearch.class.getCanonicalName()
                + " <array-size> <number-to-find>");
    }

}
