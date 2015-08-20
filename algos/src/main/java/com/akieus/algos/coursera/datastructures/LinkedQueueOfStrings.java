package com.akieus.algos.coursera.datastructures;

/**
 * @author aks
 * @since 15/08/15
 */
public class LinkedQueueOfStrings {

    public static void main(String[] args) {
        LinkedQueueOfStrings queue = new LinkedQueueOfStrings();
        String input = "to be or not to - be - - that - - - is - - the - question -";
        for(String token : input.split(" ")) {
            if (token.equals("-")) {
                System.out.print(queue.dequeue() + " ");
            } else {
                queue.enqueue(token);
            }
        }
    }

    private Node first, last = null;
    public LinkedQueueOfStrings() {
    }

    public void enqueue(String str) {
        Node newLast = new Node(str);
        if (isEmpty()) {
            first = last = newLast;
        } else {
            last.next = newLast;
            last = newLast;
        }
    }

    public String dequeue() {
        if (isEmpty()) {
            throw new IllegalStateException("Empty");
        }
        String content = first.content;
        first = first.next;
        return content;
    }

    public boolean isEmpty() {
        return first == null;
    }

    private class Node {
        private String content;
        private Node next;
        private Node(String content) {
            this.content = content;
        }
        private Node(String content, Node next) {
            this.content = content;
            this.next = next;
        }
    }
}
