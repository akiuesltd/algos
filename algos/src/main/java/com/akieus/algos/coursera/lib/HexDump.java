package com.akieus.algos.coursera.lib;

/******************************************************************************
 *  Compilation:  javac com.akieus.algos.coursera.lib.HexDump.java
 *  Execution:    java com.akieus.algos.coursera.lib.HexDump < file
 *  Dependencies: com.akieus.algos.coursera.lib.BinaryStdIn.java com.akieus.algos.coursera.lib.StdOut.java
 *  Data file:    http://algs4.cs.princeton.edu/55compression/abra.txt
 *  
 *  Reads in a binary file and writes out the bytes in hex, 16 per line.
 *
 *  % more abra.txt
 *  ABRACADABRA!
 *
 *  % java com.akieus.algos.coursera.lib.HexDump 16 < abra.txt
 *  41 42 52 41 43 41 44 41 42 52 41 21
 *  96 bits
 *
 *
 *  Remark
 *  --------------------------
 *   - Similar to the Unix utilities od (octal dump) or hexdump (hexadecimal dump).
 *
 *  % od -t x1 < abra.txt 
 *  0000000 41 42 52 41 43 41 44 41 42 52 41 21
 *  0000014
 *
 ******************************************************************************/

public class HexDump {

    public static void main(String[] args) {
        int bytesPerLine = 16;
        if (args.length == 1) {
            bytesPerLine = Integer.parseInt(args[0]);
        }

        int i;
        for (i = 0; !BinaryStdIn.isEmpty(); i++) {
            if (bytesPerLine == 0) {
                BinaryStdIn.readChar();
                continue;
            }
            if (i == 0) StdOut.printf("");
            else if (i % bytesPerLine == 0) StdOut.printf("\n", i);
            else StdOut.print(" ");
            char c = BinaryStdIn.readChar();
            StdOut.printf("%02x", c & 0xff);
        }
        if (bytesPerLine != 0) StdOut.println();
        StdOut.println((i * 8) + " bits");
    }
}
