package com.akieus.algos.coursera.lib;

/******************************************************************************
 *  Compilation:  javac com.akieus.algos.coursera.lib.Genome.java
 *  Execution:    java com.akieus.algos.coursera.lib.Genome - < input.txt   (compress)
 *  Execution:    java com.akieus.algos.coursera.lib.Genome + < input.txt   (expand)
 *  Dependencies: com.akieus.algos.coursera.lib.BinaryIn.java com.akieus.algos.coursera.lib.BinaryOut.java
 *
 *  Compress or expand a genomic sequence using a 2-bit code.
 *
 *  % more genomeTiny.txt
 *  ATAGATGCATAGCGCATAGCTAGATGTGCTAGC
 *
 *  % java com.akieus.algos.coursera.lib.Genome - < genomeTiny.txt | java com.akieus.algos.coursera.lib.Genome +
 *  ATAGATGCATAGCGCATAGCTAGATGTGCTAGC
 *
 ******************************************************************************/

public class Genome {
    private static final Alphabet DNA = new Alphabet("ACGT");

    public static void compress() { 
        String s = BinaryStdIn.readString();
        int N = s.length();
        BinaryStdOut.write(N);

        // Write two-bit code for char. 
        for (int i = 0; i < N; i++) {
            int d = DNA.toIndex(s.charAt(i));
            BinaryStdOut.write(d, 2);
        }
        BinaryStdOut.close();
    } 

    public static void expand() {
        int N = BinaryStdIn.readInt();
        // Read two bits; write char. 
        for (int i = 0; i < N; i++) {
            char c = BinaryStdIn.readChar(2);
            BinaryStdOut.write(DNA.toChar(c), 8);
        }
        BinaryStdOut.close();
    }


    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }

}
