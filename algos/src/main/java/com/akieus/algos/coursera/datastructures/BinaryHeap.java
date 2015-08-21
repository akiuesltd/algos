package com.akieus.algos.coursera.datastructures;

import java.util.Arrays;

import static com.akieus.algos.coursera.Utils.exch;
import static com.akieus.algos.coursera.Utils.less;

/**
 * @author aks
 * @since 21/08/15
 */
public class BinaryHeap {

    /*******************************************************************************/
    private int[] heap = new int[2];
    private int size;

    public BinaryHeap() {
    }

    public static void main(String[] args) {
        BinaryHeap heap = new BinaryHeap();
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
    }

    public void add(int i) {
        heap[++size] = i;
        swim(size);
        resizeIfRequired();
    }

    public void remove(int i) {
        heap[i] = heap[size--];
        // not required, only doing this to visualize it being deleted.
        heap[size + 1] = 0;
        sink(i);
    }

    private void sink(int i) {
        while (true) {
            int child1 = 2 * i;
            if (child1 > size) {
                break;
            }

            int smallerChild = child1;

            int child2 = 2 * i + 1;
            if (child2 <= size && less(heap[child2], heap[child1])) {
                smallerChild = child2;
            }
            if (heap[i] > heap[smallerChild]) {
                exch(heap, i, smallerChild);
            }
            i = smallerChild;
        }
    }

    // promote to parent while larger than parent
    private void swim(int x) {
        while (x > 1 && less(heap[x], heap[x / 2])) {
            exch(heap, x, x / 2);
            x = x / 2;
        }
    }

    private void resizeIfRequired() {
        if (isFull()) {
            int[] copy = new int[heap.length * 2];
            for (int i = 0; i < heap.length; i++) {
                copy[i] = heap[i];
            }
            heap = copy;
        }
    }

    private boolean isFull() {
        return size == heap.length - 1;
    }


}
