package com.akieus.algos.coursera.lib;

/******************************************************************************
 *  Compilation:  javac com.akieus.algos.coursera.lib.GREP.java
 *  Execution:    java com.akieus.algos.coursera.lib.GREP regexp < input.txt
 *  Dependencies: com.akieus.algos.coursera.lib.NFA.java
 *  Data files:   http://algs4.cs.princeton.edu/54regexp/tinyL.txt
 *
 *  This program takes an RE as a command-line argument and prints
 *  the lines from standard input having some substring that
 *  is in the language described by the RE. 
 *
 *  % more tinyL.txt
 *  AC
 *  AD
 *  AAA
 *  ABD
 *  ADD
 *  BCD
 *  ABCCBD
 *  BABAAA
 *  BABBAAA
 *
 *  %  java com.akieus.algos.coursera.lib.GREP "(A*B|AC)D" < tinyL.txt
 *  ABD
 *  ABCCBD
 *
 ******************************************************************************/

public class GREP {
    public static void main(String[] args) { 
        String regexp = "(.*" + args[0] + ".*)";
        NFA nfa = new NFA(regexp);
        while (StdIn.hasNextLine()) { 
            String txt = StdIn.readLine();
            if (nfa.recognizes(txt)) {
                StdOut.println(txt);
            }
        }
    } 
} 
