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

        /**
         * That should create following tree
         *            A
         *             \
         *             N
         *           /   \
         *          I     S
         *         / \    /
         *        H   L  R
         *             \
         *              M
         */

        Iterator<String> itr = tree.iterator();
        while (itr.hasNext()) {
            System.out.print(itr.next() + ", ");
        }
        System.out.println();

        tree.printTree();

        System.out.println(tree.floor("R"));
        System.out.println(tree.floor("O"));
        System.out.println(tree.floor("K"));

        // test tree size (note: there are two overwrites for A, should be 8 nodes)
        System.out.println(tree.size(tree.root));

        // test min of N
        System.out.println(tree.min(tree.root.right));

        // delete H, should have 7 elements now, and cannot find H
        tree.deleteMin(tree.root.right);
        System.out.println(tree.size(tree.root));
        System.out.println(tree.get("H"));
        // add it back and test
        tree.put("H", 6);
        System.out.println(tree.get("H"));

        tree.delete("I");
        System.out.println(tree.get("I"));

    }

    // TODO - implement properly
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
        if (node == null) {
            return new Node(key, value);
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = put(node.left, key, value);
        } else if (cmp > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value;
        }
        node.count = size(node);
        return node;
    }

    private int size(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + size(node.left) + size(node.right);
    }

    public Value get(Key key) {
        Node node = root;
        while (node != null) {
            int cmp = key.compareTo(node.key);
            if (cmp < 0) node = node.left;
            else if (cmp > 0) node = node.right;
            else return node.value;
        }
        return null;
    }

    public void delete(Key key) {
        delete(key, root);
    }

    private Node delete(Key key, Node node) {
        if (node == null) return null;

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = delete(key, node.left);
        } else if (cmp > 0) {
            node.right = delete(key, node.right);
        } else {
            if (node.left == null) {
                node = node.right;
            }
            if (node.right == null) {
                node = node.left;
            }

            Node min = min(node.right);
            min.right = deleteMin(node.right);
            min.left = node.left;
            node = min;
        }

        node.count = size(node.left) + 1 + size(node.right);
        return node;
    }

    public Node min(Node node) {
        if (node.left == null) return node;
        return min(node.left);
    }

    public Node deleteMin(Node node) {
        if (node.left == null) return node.right;

        node.left = deleteMin(node.left);
        node.count = size(node.left) + 1 + size(node.right);
        return node;
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
        private int count;

        public Node(Key key, Value value) {
            this.key = key;
            this.value = value;
            this.count = 1;
        }

        @Override
        public String toString() {
            return key.toString();
        }
    }
}
