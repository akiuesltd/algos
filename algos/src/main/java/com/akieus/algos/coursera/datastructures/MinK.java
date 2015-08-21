package com.akieus.algos.coursera.datastructures;

import com.akieus.algos.coursera.lib.StdIn;

/**
 * Uses a MaxPQ to keep track of K minimum items
 *
 * @author aks
 * @since 21/08/15
 */
public class MinK<Key extends Comparable<Key>> {

    private final int k;
    MaxPQUnordered<Key> pq = new MaxPQUnordered<>();
    public MinK(int k) {
        this.k = k;
    }

    public static void main(String[] args) {
        MinK<String> minK = new MinK<>(5);
        while (true) {
            String str = StdIn.readString();
            if (str.equalsIgnoreCase("quit") || str.equalsIgnoreCase("exit")) {
                break;
            }
            minK.add(str);
        }

        while (!minK.pq.isEmpty()) {
            System.out.println(minK.pq.delMax());
        }
    }

    public void add(Key key) {
        pq.insert(key);
        if (pq.size() > k) {
            pq.delMax();
        }
    }

}
