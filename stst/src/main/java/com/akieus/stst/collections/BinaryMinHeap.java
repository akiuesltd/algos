package com.akieus.stst.collections;

import java.util.Arrays;

import static com.akieus.stst.collections.Utils.exch;
import static com.akieus.stst.collections.Utils.less;

/**
 * Binary Heap impl.
 * <p>
 * 1) To add a new element, add it to the end, and then swim it up until it reaches it right level
 * 2) To delete, move the last element to it's position (thereby deleting it), then sink this
 * element down to it's proper place.
 * <p>
 * One difference from the algo in course - the course describes a max-heap, this is a min-heap.
 *
 * @author aks
 * @since 21/08/15
 */
public class BinaryMinHeap {

    /*******************************************************************************/
    private double[] heap = null;
    private int size;

    public BinaryMinHeap(int capacity) {
        heap = new double[capacity + 1];
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
        System.out.println("7: " + heap.find(7));
        System.out.println("4: " + heap.find(4));
        System.out.println("3: " + heap.find(3));
        System.out.println("2: " + heap.find(2));
        System.out.println("5: " + heap.find(5));

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
                    && less(heap[child + 1], heap[child])) { // and other is smaller
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
