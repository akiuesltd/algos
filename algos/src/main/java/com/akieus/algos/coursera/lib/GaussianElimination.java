package com.akieus.algos.coursera.lib;

/******************************************************************************
 *  Compilation:  javac com.akieus.algos.coursera.lib.GaussianElimination.java
 *  Execution:    java com.akieus.algos.coursera.lib.GaussianElimination
 *  Dependencies: com.akieus.algos.coursera.lib.StdOut.java
 * 
 *  Gaussian elimination with partial pivoting.
 *
 *  % java com.akieus.algos.coursera.lib.GaussianElimination
 *  -1.0
 *  2.0
 *  2.0
 *
 ******************************************************************************/

public class GaussianElimination {
    private static final double EPSILON = 1e-10;

    // Gaussian elimination with partial pivoting
    public static double[] lsolve(double[][] A, double[] b) {
        int N  = b.length;

        for (int p = 0; p < N; p++) {

            // find pivot row and swap
            int max = p;
            for (int i = p + 1; i < N; i++) {
                if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
                    max = i;
                }
            }

            swap(A, p, max);
            swap(b, p, max);

            // singular or nearly singular
            if (Math.abs(A[p][p]) <= EPSILON) {
                throw new ArithmeticException("Matrix is singular or nearly singular");
            }

            // pivot within A and b
            for (int i = p + 1; i < N; i++) {
                double alpha = A[i][p] / A[p][p];
                b[i] -= alpha * b[p];
                for (int j = p; j < N; j++) {
                    A[i][j] -= alpha * A[p][j];
                }
            }
        }

        // back substitution
        double[] x = new double[N];
        for (int i = N - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < N; j++) {
                sum += A[i][j] * x[j];
            }
            x[i] = (b[i] - sum) / A[i][i];
        }
        return x;
    }

    // swap entries a[i] and a[j] in array a[]
    private static void swap(double[] a, int i, int j) {
        double temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    // swap rows A[i][] and A[j][] in 2D array A[][]
    private static void swap(double[][] A, int i, int j) {
        double[] temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    // sample client
    public static void main(String[] args) {
        int N = 3;
        double[][] A = {
            { 0, 1,  1 },
            { 2, 4, -2 },
            { 0, 3, 15 }
        };
        double[] b = { 4, 2, 36 };
        double[] x = lsolve(A, b);


        // print results
        for (int i = 0; i < N; i++) {
            StdOut.println(x[i]);
        }

    }

}
