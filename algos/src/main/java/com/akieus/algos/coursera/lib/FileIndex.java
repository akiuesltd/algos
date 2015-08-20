package com.akieus.algos.coursera.lib; /******************************************************************************
 *  Compilation:  javac com.akieus.algos.coursera.lib.FileIndex.java
 *  Execution:    java com.akieus.algos.coursera.lib.FileIndex file1.txt file2.txt file3.txt ...
 *  Dependencies: com.akieus.algos.coursera.lib.ST.java com.akieus.algos.coursera.lib.SET.java com.akieus.algos.coursera.lib.In.java com.akieus.algos.coursera.lib.StdIn.java com.akieus.algos.coursera.lib.StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/35applications/ex1.txt
 *                http://algs4.cs.princeton.edu/35applications/ex2.txt
 *                http://algs4.cs.princeton.edu/35applications/ex3.txt
 *                http://algs4.cs.princeton.edu/35applications/ex4.txt
 *
 *  % java com.akieus.algos.coursera.lib.FileIndex ex*.txt
 *  age
 *   ex3.txt
 *   ex4.txt 
 * best
 *   ex1.txt 
 * was
 *   ex1.txt
 *   ex2.txt
 *   ex3.txt
 *   ex4.txt 
 *
 *  % java com.akieus.algos.coursera.lib.FileIndex *.txt
 *
 *  % java com.akieus.algos.coursera.lib.FileIndex *.java
 *
 ******************************************************************************/

import java.io.File;

public class FileIndex { 

    public static void main(String[] args) {

        // key = word, value = set of files containing that word
        ST<String, SET<File>> st = new ST<String, SET<File>>();

        // create inverted index of all files
        StdOut.println("Indexing files");
        for (String filename : args) {
            StdOut.println("  " + filename);
            File file = new File(filename);
            In in = new In(file);
            while (!in.isEmpty()) {
                String word = in.readString();
                if (!st.contains(word)) st.put(word, new SET<File>());
                SET<File> set = st.get(word);
                set.add(file);
            }
        }


        // read queries from standard input, one per line
        while (!StdIn.isEmpty()) {
            String query = StdIn.readString();
            if (st.contains(query)) {
                SET<File> set = st.get(query);
                for (File file : set) {
                    StdOut.println("  " + file.getName());
                }
            }
        }

    }

}
