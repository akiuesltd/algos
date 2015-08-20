package com.akieus.algos.coursera.lib; /******************************************************************************
 *  Compilation:  javac com.akieus.algos.coursera.lib.DoublingTest.java
 *  Execution:    java com.akieus.algos.coursera.lib.DoublingTest
 *  Dependencies: com.akieus.algos.coursera.lib.ThreeSum.java com.akieus.algos.coursera.lib.Stopwatch.java com.akieus.algos.coursera.lib.StdRandom.java com.akieus.algos.coursera.lib.StdOut.java
 *
 *  % java com.akieus.algos.coursera.lib.DoublingTest
 *      250   0.0
 *      500   0.0
 *     1000   0.1
 *     2000   0.6
 *     4000   4.5
 *     8000  35.7
 *  ...
 *
 ******************************************************************************/

/**
 *  The <tt>com.akieus.algos.coursera.lib.DoublingTest</tt> class provides a client for measuring
 *  the running time of a method using a doubling test.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/14analysis">Section 1.4</a>
 *  of <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class DoublingTest {
    private static final int MAXIMUM_INTEGER = 1000000;

    // This class should not be instantiated.
    private DoublingTest() { }

    /**
     * Returns the amount of time to call <tt>com.akieus.algos.coursera.lib.ThreeSum.count()</tt> with <em>N</em>
     * random 6-digit integers.
     * @param N the number of integers
     * @return amount of time (in seconds) to call <tt>com.akieus.algos.coursera.lib.ThreeSum.count()</tt>
     *   with <em>N</em> random 6-digit integers
     */
    public static double timeTrial(int N) {
        int[] a = new int[N];
        for (int i = 0; i < N; i++) {
            a[i] = StdRandom.uniform(-MAXIMUM_INTEGER, MAXIMUM_INTEGER);
        }
        Stopwatch timer = new Stopwatch();
        ThreeSum.count(a);
        return timer.elapsedTime();
    }

    /**
     * Prints table of running times to call <tt>com.akieus.algos.coursera.lib.ThreeSum.count()</tt>
     * for arrays of size 250, 500, 1000, 2000, and so forth.
     */
    public static void main(String[] args) { 
        for (int N = 250; true; N += N) {
            double time = timeTrial(N);
            StdOut.printf("%7d %5.1f\n", N, time);
        } 
    } 
} 

