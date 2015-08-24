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
        System.out.println(tree.get("M"));
        System.out.println(tree.get("K"));
    }

    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    private Node put(Node node, Key key, Value value) {
        if (node == null) return new Node(key, value);
        int cmp = node.key.compareTo(key);
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
