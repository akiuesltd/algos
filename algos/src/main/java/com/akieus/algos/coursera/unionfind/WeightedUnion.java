package com.akieus.algos.coursera.unionfind;

import static com.akieus.utils.Utils.printArray;

/**
 * @author aks
 * @since 14/08/15
 */
public class WeightedUnion {
    public static void main(String[] args) {
        WeightedUnion qf = new WeightedUnion(10);
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
    private final int[] size;

    public WeightedUnion(int n) {
        id = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            id[i] = i;
            size[i] = 1;
        }
    }

    public void connect(int one, int two) {
        int rootOne = root(one);
        int rootTwo = root(two);
        // missed following
        if (rootOne == rootTwo) {
            return;
        }

        int smaller = size[rootOne] < size[rootTwo] ? rootOne : rootTwo;
        int larger = smaller == rootOne ? rootTwo : rootOne;

        id[smaller] = larger;
        size[larger] = size[larger] + size[smaller];
    }

    public boolean isConnected(int one, int two) {
        return root(one) == root(two);
    }

    public int root(int i) {
        while(i != id[i]) {
            i = id[i];
        }
        return i;
    }

}
