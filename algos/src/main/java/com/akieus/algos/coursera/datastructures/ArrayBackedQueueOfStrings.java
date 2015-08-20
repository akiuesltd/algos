package com.akieus.algos.coursera.datastructures;

import java.io.PrintStream;

/**
 * @author aks
 * @since 15/08/15
 */
public class ArrayBackedQueueOfStrings {

    private String[] arr = new String[1];
    private int head = -1;
    private int tail = -1;
    public ArrayBackedQueueOfStrings() {
    }

    public static void main(String[] args) {
        try {
            ArrayBackedQueueOfStrings queue = new ArrayBackedQueueOfStrings();
            String input = "to be or not to - be - - that - - - is - - the - question -";
            for (String token : input.split(" ")) {
                if (token.equals("-")) {
                    System.out.print(queue.dequeue() + " ");
                } else {
                    queue.enqueue(token);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(new PrintStream(System.out));
        }
    }

    public void enqueue(String str) {
        if (head == arr.length - 1) {
            resize();
        }
        arr[++head] = str;
        if (tail == -1) {
            tail = head;
        }
    }

    public String dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException();
        }

        String content = arr[tail];
        arr[tail] = null;
        tail++;

        // shrink
        if (size() < arr.length / 4) {
            resize();
        }
        return content;
    }

    public boolean isEmpty() {
        return head == -1;
    }

    public int size() {
        if (isEmpty()) {
            return 0;
        }
        return head - tail + 1;
    }

    private void resize() {
        if (isEmpty() || size() == 0) {
            arr = new String[1];
            head = tail = -1;
        } else {
            String[] copy = new String[2 * size()];
            for (int i = tail, j = 0; i <= head;) {
                copy[j++] = arr[i++];
            }
            arr = copy;
            head = head - tail;
            tail = 0;
        }
    }
}
