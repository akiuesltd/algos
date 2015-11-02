package com.akieus.stst.collections;

import java.util.Arrays;

public class BinaryMinHeap extends AbstractHeap {

    public BinaryMinHeap(int capacity) {
        super(capacity);
    }

    public static void main(String[] args) {
        BinaryMinHeap heap = new BinaryMinHeap(8);
        heap.add(7);
        heap.add(1);
        heap.add(4);
        heap.add(2);
        heap.add(3);
        heap.add(5);
        heap.add(6);

        System.out.println(Arrays.toString(heap.heap));
        System.out.println("7: " + heap.indexOf(7));
        System.out.println("4: " + heap.indexOf(4));
        System.out.println("3: " + heap.indexOf(3));
        System.out.println("2: " + heap.indexOf(2));
        System.out.println("5: " + heap.indexOf(5));

        heap.remove(3);
        System.out.println(Arrays.toString(heap.heap));
        heap.remove(7);
        System.out.println(Arrays.toString(heap.heap));
        heap.remove(4);
        heap.remove(5);
        System.out.println(Arrays.toString(heap.heap));
    }

    protected boolean less(int i, int j) {
        return heap[i] > heap[j];
    }


}
