package com.akieus.algos.coursera.lib;

/******************************************************************************
 *  Compilation:  javac com.akieus.algos.coursera.lib.LRS.java
 *  Execution:    java com.akieus.algos.coursera.lib.LRS < file.txt
 *  Dependencies: com.akieus.algos.coursera.lib.StdIn.java com.akieus.algos.coursera.lib.SuffixArray.java
 *  Data files:   http://algs4.cs.princeton.edu/63suffix/tinyTale.txt
 *                http://algs4.cs.princeton.edu/63suffix/mobydick.txt
 *  
 *  Reads a text string from stdin, replaces all consecutive blocks of
 *  whitespace with a single space, and then computes the longest
 *  repeated substring in that text using a suffix array.
 * 
 *  % java com.akieus.algos.coursera.lib.LRS < tinyTale.txt
 *  'st of times it was the '
 *
 *  % java com.akieus.algos.coursera.lib.LRS < mobydick.txt
 *  ',- Such a funny, sporty, gamy, jesty, joky, hoky-poky lad, is the Ocean, oh! Th'
 * 
 *  % java com.akieus.algos.coursera.lib.LRS
 *  aaaaaaaaa
 *  'aaaaaaaa'
 *
 *  % java com.akieus.algos.coursera.lib.LRS
 *  abcdefg
 *  ''
 *
 ******************************************************************************/


public class LRS {

    public static void main(String[] args) {
        String text = StdIn.readAll().replaceAll("\\s+", " ");
        SuffixArray sa = new SuffixArray(text);

        int N = sa.length();

        String lrs = "";
        for (int i = 1; i < N; i++) {
            int length = sa.lcp(i);
            if (length > lrs.length()) {
                // lrs = sa.select(i).substring(0, length);
                lrs = text.substring(sa.index(i), sa.index(i) + length);
            }
        }
        
        StdOut.println("'" + lrs + "'");
    }
}
