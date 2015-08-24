package com.akieus.algos.coursera.datastructures;

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

        System.out.println(tree.get("A"));
        System.out.println(tree.get("N"));
        System.out.println(tree.get("K"));
    }

    public Value put(Key key, Value value) {
        // special case for first entry.
        Node newNode = new Node(key, value);
        if (root == null) {
            root = newNode;
            return null;
        }

        Node existing = search(key, root, null);


        int cmp = key.compareTo(existing.key);
        if (cmp == 0) {
            Value oldValue = existing.value;
            existing.value = value;
            return oldValue;
        } else if (cmp < 0) {
            existing.left = newNode;
        } else {
            existing.right = newNode;
        }
        return null;
    }

    public Value get(Key key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (root == null) {
            return null;
        }

        Node node = search(key, root, null);
        if (key.equals(node.key)) {
            return node.value;
        }
        return null;
    }

    private Node search(Key key, Node node, Node parent) {
        if (node == null) {
            return parent;
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return node;
        } else if (cmp < 0) {
            return search(key, node.left, node);
        } else {
            return search(key, node.right, node);
        }
    }

    public Value delete(Key key) {
        throw new UnsupportedOperationException();
    }

    public Iterator<Key> iterator() {
        throw new UnsupportedOperationException();
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
    }
}
