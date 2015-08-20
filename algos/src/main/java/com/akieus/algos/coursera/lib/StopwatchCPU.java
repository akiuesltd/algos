package com.akieus.algos.coursera.lib; /******************************************************************************
 *  Compilation:  javac com.akieus.algos.coursera.lib.StopwatchCPU.java
 *  Execution:    none
 *  Dependencies: none
 *
 *
 *  A utility data type to measure the running time of a program. This class
 *  is similar to com.akieus.algos.coursera.lib.Stopwatch.java but it uses CPU time instead of wall clock time.
 *
 ******************************************************************************/

import java.lang.management.ThreadMXBean;
import java.lang.management.ManagementFactory;

/**
 *  <i>com.akieus.algos.coursera.lib.StopwatchCPU</i>. This class is a data type for measuring
 *  the running time (CPU time of a single thread) of a program.
 *  <p>
 *  For additional documentation, see
 *  <a href="http://introcs.cs.princeton.edu/32class">Section 3.2</a> of
 *  <i>Introduction to Programming in Java: An Interdisciplinary Approach</i>
 *  by Robert Sedgewick and Kevin Wayne.
 */


public class StopwatchCPU {
    private static final double NANOSECONDS = 1000000000;

    private final ThreadMXBean threadTimer;
    private final long start;

   /**
     * Create a stopwatch object.
     */
    public StopwatchCPU() {
        threadTimer = ManagementFactory.getThreadMXBean();
        start = threadTimer.getCurrentThreadCpuTime();
    }

   /**
     * Return CPU time (in seconds) since this object was created.
     */
    public double elapsedTime() {
        long now = threadTimer.getCurrentThreadCpuTime();
        return (now - start) / NANOSECONDS;
    }        
}




