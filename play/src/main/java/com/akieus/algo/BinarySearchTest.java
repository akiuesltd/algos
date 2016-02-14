package com.akieus.algo;

public class BinarySearchTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        test();
    }

    private static void test() {
        result("10, 2", new BinarySearch(10).search(2) == 2);
        result("10, 15", new BinarySearch(10).search(15) == -1);
        result("10, -5", new BinarySearch(10).search(-5) == -1);
        result("10, -1", new BinarySearch(10).search(-1) == -1);
        result("10, 0", new BinarySearch(10).search(0) == 0);
        result("10, 9", new BinarySearch(10).search(9) == 9);
    }

    private static void result(String testcase, boolean passed) {
        System.out.println(testcase + ": " + (passed ? "Passed" : "Failed"));
    }

}
