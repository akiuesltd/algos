package com.akieus.algos.coursera.sort;

import java.util.Arrays;

import static com.akieus.algos.coursera.Utils.*;

/**
 * Binary Heap impl.
 * <p>
 * 1) To add a new element, add it to the end, and then swim it up until it reaches it right level
 * 2) To delete, move the last element to it's position (thereby deleting it), then sink this
 * element down to it's proper place.
 * <p>
 *
 * @author aks
 * @since 21/08/15
 */
public class BinaryHeapSort {

    /*******************************************************************************/
    private int[] heap = new int[2];
    private int size;

    public BinaryHeapSort() {
    }

    public static void main(String[] args) {
        BinaryHeapSort heap = new BinaryHeapSort();
        heap.add(1);
        heap.add(4);
        heap.add(2);
        heap.add(3);
        heap.add(5);
        heap.add(6);
        heap.add(7);

        System.out.println(Arrays.toString(heap.heap));

        heap.remove(1);
        System.out.println(Arrays.toString(heap.heap));
        heap.remove(3);
        System.out.println(Arrays.toString(heap.heap));
        heap.remove(1);
        heap.remove(1);
        System.out.println(Arrays.toString(heap.heap));
    }

    public void add(int i) {
        heap[++size] = i;
        swim(size);
        heap = resizeIfRequired(heap, size + 1);
    }

    public void remove(int i) {
        if (i > size) {
            throw new IllegalArgumentException();
        }
        heap[i] = heap[size--];
        // not required, only doing this to visualize it being deleted.
        heap[size + 1] = 0;
        sink(i);
        heap = shrinkIfCan(heap, size + 1);
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
