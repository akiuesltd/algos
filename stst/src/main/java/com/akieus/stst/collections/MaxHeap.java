package com.akieus.stst.collections;

public class MaxHeap extends AbstractHeap {

    public MaxHeap(int capacity) {
        super(capacity);
    }

    protected boolean less(int i, int j) {
        return heap[i] < heap[j];
    }
}
