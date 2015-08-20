package com.akieus.algos.coursera.lib; /******************************************************************************
 *  Compilation:  javac com.akieus.algos.coursera.lib.Cat.java
 *  Execution:    java com.akieus.algos.coursera.lib.Cat input0.txt input1.txt ... output.txt
 *  Dependencies: com.akieus.algos.coursera.lib.In.java com.akieus.algos.coursera.lib.Out.java
 *
 *  Reads in text files specified as the first command-line 
 *  arguments, concatenates them, and writes the result to
 *  filename specified as the last command-line arguments.
 *
 *  % more in1.txt
 *  This is
 *
 *  % more in2.txt 
 *  a tiny
 *  test.
 * 
 *  % java com.akieus.algos.coursera.lib.Cat in1.txt in2.txt out.txt
 *
 *  % more out.txt
 *  This is
 *  a tiny
 *  test.
 *
 ******************************************************************************/

/**
 *  The <tt>com.akieus.algos.coursera.lib.Cat</tt> class provides a client for concatenating the results
 *  of several text files.
 *  <p>
 *  For additional documentation, see <a href="http://algs4.cs.princeton.edu/11model">Section 1.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 */
public class Cat { 

    // this class should not be instantiated
    private Cat() { }

    /**
     * Reads in a sequence of text files specified as the first command-line
     * arguments, concatenates them, and writes the results to the file
     * specified as the last command-line argument.
     */
    public static void main(String[] args) { 
        Out out = new Out(args[args.length - 1]);
        for (int i = 0; i < args.length - 1; i++) {
            In in = new In(args[i]);
            String s = in.readAll();
            out.println(s);
            in.close();
        }
        out.close();
    }

}
