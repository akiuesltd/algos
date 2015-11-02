package com.akieus.stst.collections;

public class MinHeap extends AbstractHeap {

    public MinHeap(int capacity) {
        super(capacity);
    }

    protected boolean less(int i, int j) {
        return heap[i] > heap[j];
    }
}
