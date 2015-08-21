package com.akieus.algos.coursera.datastructures;

import java.util.Arrays;

import static com.akieus.algos.coursera.Utils.*;

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
public class MinPQBinaryHeap<Key extends Comparable<Key>> {

    private Key[] heap = (Key[]) new Comparable[2];
    private int size = 0;

    public MinPQBinaryHeap() {
    }

    public static void main(String[] args) {
        MinPQBinaryHeap<String> pq = new MinPQBinaryHeap<>();
        pq.insert("A");
        pq.insert("Z");
        pq.insert("T");
        pq.insert("B");
        pq.insert("Q");
        pq.insert("M");
        pq.insert("L");

        System.out.println("Insert done...");

        System.out.println(pq.min());
        System.out.println(Arrays.toString(pq.heap));
        System.out.println(pq.delMin());
        System.out.println(Arrays.toString(pq.heap));
    }

    public void insert(Key key) {
        heap[++size] = key;
        swim(size);
        heap = resizeIfRequired(heap, size + 1);
    }

    public Key remove(int i) {
        if (i > size) {
            throw new IllegalArgumentException();
        }

        Key removed = heap[i];
        heap[i] = heap[size--];
        // not required, only doing this to visualize it being deleted.
        heap[size + 1] = null;
        sink(i);
        heap = shrinkIfCan(heap, size + 1);
        return removed;
    }

    public Key delMin() {
        return remove(1);
    }

    public Key min() {
        return heap[1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void sink(int i) {
        while (2 * i <= size) {
            int child = 2 * i;
            if (child < size && less(heap[child + 1], heap[child])) {
                child++;
            }
            if (less(heap[child], heap[i])) {
                exch(heap, i, child);
            }
            i = child;
        }
    }

    // promote to parent while larger than parent
    private void swim(int x) {
        while (x > 1 && less(heap[x], heap[x / 2])) {
            exch(heap, x, x / 2);
            x = x / 2;
        }
    }
}
