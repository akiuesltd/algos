package com.akieus.stst.collections;

import java.util.Arrays;

import static com.akieus.stst.collections.Utils.exch;
import static com.akieus.stst.collections.Utils.less;

public class BinaryMaxHeap {

    /*******************************************************************************/
    private double[] heap = null;
    private int size;

    public BinaryMaxHeap(int capacity) {
        heap = new double[capacity + 1];
    }

    public static void main(String[] args) {
        BinaryMaxHeap heap = new BinaryMaxHeap(8);
        heap.add(7);
        heap.add(1);
        heap.add(4);
        heap.add(2);
        heap.add(3);
        heap.add(5);
        heap.add(6);

        System.out.println(Arrays.toString(heap.heap));
        System.out.println("7: " + heap.find(7));
        System.out.println("4: " + heap.find(4));
        System.out.println("3: " + heap.find(3));
        System.out.println("2: " + heap.find(2));
        System.out.println("5: " + heap.find(5));
        System.out.println("20: " + heap.find(20));

        heap.remove(3);
        System.out.println(Arrays.toString(heap.heap));
        heap.remove(7);
        System.out.println(Arrays.toString(heap.heap));
        heap.remove(4);
        heap.remove(5);
        System.out.println(Arrays.toString(heap.heap));
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
        int index = find(value);
        if (index != 0) {
            heap[index] = heap[size--];
            // not required, only doing this to visualize it being deleted.
            heap[size + 1] = 0;
            sink(index);
        }
        return index;
    }

    // can be improved to log(n), but for a max size of 8, constant might be higher (to be validated)
    private int find(double value) {
        for (int i = 1; i <= size; i++) {
            if (heap[i] == value) {
                return i;
            }
        }
        return 0;
    }

    private void sink(int i) {
        while (2 * i <= size) {
            int child = 2 * i;
            if (child < size // i.e the other child exists
                    && less(heap[child], heap[child + 1])) { // and other is smaller
                child++;
            }
            if (less(heap[i], heap[child])) {
                exch(heap, i, child);
            }
            i = child;
        }
    }

    // promote to parent while larger than parent
    private void swim(int x) {
        while (x > 1 && less(heap[x / 2], heap[x])) {
            exch(heap, x, x / 2);
            x = x / 2;
        }
    }
}
