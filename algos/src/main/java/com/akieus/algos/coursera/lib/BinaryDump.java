package com.akieus.algos.coursera.lib;

/******************************************************************************
 *  Compilation:  javac com.akieus.algos.coursera.lib.BinaryDump.java
 *  Execution:    java com.akieus.algos.coursera.lib.BinaryDump N < file
 *  Dependencies: com.akieus.algos.coursera.lib.BinaryStdIn.java
 *  Data file:    http://introcs.cs.princeton.edu/stdlib/abra.txt
 *  
 *  Reads in a binary file and writes out the bits, N per line.
 *
 *  % more abra.txt 
 *  ABRACADABRA!
 *
 *  % java com.akieus.algos.coursera.lib.BinaryDump 16 < abra.txt
 *  0100000101000010
 *  0101001001000001
 *  0100001101000001
 *  0100010001000001
 *  0100001001010010
 *  0100000100100001
 *  96 bits
 *
 ******************************************************************************/

public class BinaryDump {

    public static void main(String[] args) {
        int bitsPerLine = 16;
        if (args.length == 1) {
            bitsPerLine = Integer.parseInt(args[0]);
        }

        int count;
        for (count = 0; !BinaryStdIn.isEmpty(); count++) {
            if (bitsPerLine == 0) {
                BinaryStdIn.readBoolean();
                continue;
            }
            else if (count != 0 && count % bitsPerLine == 0) StdOut.println();
            if (BinaryStdIn.readBoolean()) StdOut.print(1);
            else                           StdOut.print(0);
        }
        if (bitsPerLine != 0) StdOut.println();
        StdOut.println(count + " bits");
    }
}
