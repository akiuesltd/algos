package com.akieus.algos.coursera.unionfind;

import static com.akieus.utils.Utils.printArray;

/**
 * @author aks
 * @since 13/08/15
 */
public class QuickUnion {
    public static void main(String[] args) {
        QuickUnion qf = new QuickUnion(10);
        printArray(qf.id);

        qf.connect(2, 9);
        qf.connect(4, 9);
        qf.connect(3, 4);
        qf.connect(5, 6);

        printArray(qf.id);
        System.out.println(qf.isConnected(3, 6)); // false

        qf.connect(3, 6); // connect root of 3 to 6
        printArray(qf.id);
        System.out.println(qf.isConnected(3, 6)); // true
        System.out.println(qf.isConnected(6, 9)); // true

        System.out.println(qf.isConnected(3, 7)); // false
    }

    private final int[] id;

    public QuickUnion(int n) {
        id = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
        }
    }

    public void connect(int one, int two) {
//        id[root(one)] = two;
        id[root(one)] = root(two);
    }

    public boolean isConnected(int one, int two) {
        return root(one) == root(two);
    }

    public int root(int i) {
//        recursive solution
//        if (id[i] == i) {
//            return i;
//        }
//        return root(id[i]);

        int parent = i;
        while(parent != id[parent]) {
            parent = id[parent];
        }
        return parent;
    }
}

