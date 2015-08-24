package com.akieus.algos.coursera.datastructures;

import com.akieus.algos.coursera.lib.Queue;

import java.util.Iterator;

/**
 * @author aks
 * @since 24/08/15
 */
public class BinarySearchTree<Key extends Comparable<Key>, Value> {


    /*****************************************************/

    private Node root;


    public BinarySearchTree() {
    }

    public static void main(String[] args) {
        BinarySearchTree<String, Integer> tree = new BinarySearchTree<>();
        tree.put("A", 1);
        tree.put("N", 2);
        tree.put("I", 3);
        tree.put("L", 4);
        tree.put("S", 5);
        tree.put("H", 6);
        tree.put("A", 7);
        tree.put("R", 8);
        tree.put("M", 9);
        tree.put("A", 10);

        Iterator<String> itr = tree.iterator();
        while (itr.hasNext()) {
            System.out.print(itr.next() + ", ");
        }
        System.out.println();

        tree.printTree();

        System.out.println(tree.floor("R"));
        System.out.println(tree.floor("O"));
        System.out.println(tree.floor("K"));
    }

    private void printTree() {
        Queue<Node> queue = new Queue<>();
        traverseNodes(queue, root);
        for (Node node : queue) {
            System.out.println(node.key + "> " + node.left + ", " + node.right);
        }
    }

    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    private Node put(Node node, Key key, Value value) {
        if (node == null) return new Node(key, value);
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = put(node.left, key, value);
        } else if (cmp > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value;
        }
        return node;
    }

    public Value get(Key key) {
        Node node = root;
        while (node != null) {
            int cmp = node.key.compareTo(key);
            if (cmp < 0) node = node.left;
            else if (cmp > 0) node = node.right;
            else return node.value;
        }
        return null;
    }

    public Value delete(Key key) {
        throw new UnsupportedOperationException();
    }

    public Key floor(Key key) {
        Node node = floor(key, root);
        return node == null ? null : node.key;
    }

    private Node floor(Key key, Node node) {
        if (node == null) return null;

        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return node;
        } else if (cmp < 0) {
            return floor(key, node.left);
        } else {
            Node floor = floor(key, node.right);
            return floor == null ? node : floor;
        }
    }

    public Iterator<Key> iterator() {
        Queue<Key> queue = new Queue<>();
        traverse(queue, root);
        return queue.iterator();
    }

    private void traverse(Queue<Key> queue, Node node) {
        if (node == null) return;
        traverse(queue, node.left);
        queue.enqueue(node.key);
        traverse(queue, node.right);
    }

    private void traverseNodes(Queue<Node> queue, Node node) {
        if (node == null) return;
        traverseNodes(queue, node.left);
        queue.enqueue(node);
        traverseNodes(queue, node.right);
    }

    private class Node {
        private Key key;
        private Value value;
        private Node left;
        private Node right;

        public Node(Key key, Value value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return key.toString();
        }
    }
}
