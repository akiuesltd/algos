package com.akieus.stst.collections;

import java.util.Arrays;

public class BinaryMaxHeap extends AbstractHeap {

    public BinaryMaxHeap(int capacity) {
        super(capacity);
    }

    public static void main(String[] args) {
        BinaryMaxHeap heap = new BinaryMaxHeap(8);
        heap.add(1);
        heap.add(4);
        heap.add(2);
        heap.add(3);
        heap.add(5);
        heap.add(6);
        heap.add(7);

        System.out.println(heap.isMaxHeap());
        System.out.println(Arrays.toString(heap.heap));

        heap.remove(1);
        System.out.println(heap.isMaxHeap());
        System.out.println(Arrays.toString(heap.heap));
        heap.remove(3);
        System.out.println(heap.isMaxHeap());
        System.out.println(Arrays.toString(heap.heap));
        heap.remove(1);
        System.out.println(heap.isMaxHeap());
        System.out.println(Arrays.toString(heap.heap));

    }

    protected boolean less(int i, int j) {
        return heap[i] < heap[j];
    }


}
