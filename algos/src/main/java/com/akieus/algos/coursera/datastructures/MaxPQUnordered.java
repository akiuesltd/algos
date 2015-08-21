package com.akieus.algos.coursera.datastructures;

/**
 * An elementary implementation of Max priority queue. Elements aren't kept in ordered
 * sequence, making inserts amortised constant time while delete or find operations are O(n).
 * <p>
 * Uses a resizing array backed implementation (just to make life a little more difficult than
 * using a linked list).
 *
 * @author aks
 * @since 21/08/15
 */
public class MaxPQUnordered<Key extends Comparable<Key>> {

    public static void main(String[] args) {
        MaxPQUnordered<String> pq = new MaxPQUnordered<>();
        pq.insert("A");
        pq.insert("Z");
        pq.insert("T");
        pq.insert("B");
        pq.insert("Q");
        pq.insert("M");
        pq.insert("L");

        System.out.println("Insert done...");

        System.out.println(pq.max());
        while (!pq.isEmpty()) {
            System.out.println(pq.delMax());
        }
    }



    private Key[] arr = (Key[]) new Comparable[1];
    private int size = 0;

    public MaxPQUnordered() {
    }

    public void insert(Key key) {
        resizeIfRequired();
        arr[size++] = key;
    }

    public Key delMax() {
        if (isEmpty()) {
            throw new IllegalStateException();
        }

        int m = findMax();
        Key max = arr[m];

        for (int i = m; i < size - 1; i++) {
            arr[i] = arr[i + 1];
        }
        size--;
        shrinkIfCan();
        return max;
    }

    public Key max() {
        return arr[findMax()];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    // assumes non-empty array.
    private int findMax() {
        int i = 0, m = 0;
        while (i < size) {
            if (arr[i].compareTo(arr[m]) > 0) {
                m = i;
            }
            i++;
        }
        return m;
    }

    private void resizeIfRequired() {
        if (isFull()) {
            Key[] copy = (Key[]) (new Comparable[size * 2]);
            for (int i = 0; i < size; i++) {
                copy[i] = arr[i];
            }
            arr = copy;
        }
    }

    private void shrinkIfCan() {
        if (canShrink()) {
            Key[] copy = (Key[]) (new Comparable[size * 2]);
            for (int i = 0; i < size; i++) {
                copy[i] = arr[i];
            }
            arr = copy;
        }
    }

    private boolean isFull() {
        return size == arr.length;
    }

    private boolean canShrink() {
        return size < arr.length / 4;
    }

}
