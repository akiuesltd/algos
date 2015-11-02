package com.akieus.stst.collections;

import java.util.Arrays;

public abstract class AbstractHeap {

    protected double[] heap = null;
    protected int size;

    public AbstractHeap(int capacity) {
        heap = new double[capacity + 1];
    }

    public void reset() {
        for (int i = 0; i < heap.length; i++) {
            heap[i] = 0;
        }
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public double root() {
        if (isEmpty()) {
            return Double.NaN;
        }
        return heap[1];
    }

    public void add(double i) {
        heap[++size] = i;
        swim(size);
    }

    public double removeRoot() {
        if (isEmpty()) {
            return Double.NaN;
        }
        double root = root();
        heap[1] = heap[size--];
        // not required, only doing this to visualize it being deleted.
        heap[size + 1] = 0;
        sink(1);
        return root;
    }

    public int remove(double value) {
        int index = indexOf(value);
        if (index != 0) {
            removeAt(index);
        }

        return index;
    }

    private void removeAt(final int index) {
        heap[index] = heap[size--];

        if (index > 1 && less(index / 2, index)) {
            swim(index);
        }else{
            sink(index);
        }
        // not required, only doing this to visualize it being deleted.
        heap[size + 1] = 0;
    }

    // can be improved to log(n), but for a max size of 8, constant might be higher (to be validated)
    protected int indexOf(double value) {
        for (int i = 1; i <= size; i++) {
            if (heap[i] == value) {
                return i;
            }
        }
        return 0;
    }

    protected void sink(int k) {
        while (2 * k <= size) {
            int j = 2 * k;
            if (j < size && less(j, j + 1)) j++;
            if (!less(k, j)) break;
            exch(k, j);
            k = j;
        }
    }

    // promote to parent while larger than parent
    private void swim(int k) {
        while (k > 1 && less(k / 2, k)) {
            exch(k, k / 2);
            k = k / 2;
        }
    }

    public boolean isMaxHeap() {
        return isMaxHeap(1);
    }

    private boolean isMaxHeap(int k) {
        if (k > size) return true;
        int left = 2 * k, right = 2 * k + 1;
        if (left <= size && less(k, left)) return false;
        if (right <= size && less(k, right)) return false;
        return isMaxHeap(left) && isMaxHeap(right);
    }

    private void exch(int i, int j) {
        double swap = heap[i];
        heap[i] = heap[j];
        heap[j] = swap;
    }

    protected abstract boolean less(int i, int j);

    public void printHeap() {
        System.out.println(Arrays.toString(heap));
    }
}
