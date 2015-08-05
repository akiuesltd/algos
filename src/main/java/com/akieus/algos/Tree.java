package com.akieus.algos;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.*;

import static com.akieus.algos.Tree.NodeFactory.Node;

/**
 * Created by aks on 05-Aug-15.
 */
public class Tree {

    public static void main(final String[] args) {
        List<Node<Integer>> list = new LinkedList<>();
        visitDepthFirst(buildUnidirectionTestTree(), list);
        printNodes(list);
        Set<Node<Integer>> set = new TreeSet<>(new Comparator<Node<Integer>>() {
            @Override
            public int compare(Node<Integer> o1, Node<Integer> o2) {
                return o1.id.compareTo(o2.id);
            }
        });
        visitBreadthFirst(buildUnidirectionTestTree(), set);
        printNodes(set);
    }

    private static <T> void visitBreadthFirst(final Node<T> node, final Set<Node<T>> set) {
        set.add(node);
        List<Node<T>> children = node.children;
        if (children != null && !children.isEmpty()) {
            set.addAll(children);
            for (Node<T> child : children) {
                visitBreadthFirst(child, set);
            }
        }
    }

    private static <T> void visitDepthFirst(final Node<T> node, final List<Node<T>> list) {
        list.add(node);
        List<Node<T>> children = node.children;
        if (children != null && !children.isEmpty()) {
            for (Node<T> child : children) {
                visitDepthFirst(child, list);
            }
        }
    }

    private static <T> void printNodes(Collection<Node<T>> nodes) {
        for (Node<T> node : nodes) {
            System.out.print(node.id + ", ");
        }
        System.out.println();
    }


    public static Node<Integer> buildUnidirectionTestTree() {
        return Node(1)
                .addChild(Node(2)
                        .addChild(4)
                        .addChild(Node(5)
                                .addChild(Node(8).addChild(9)))
                        .addChild(6))
                .addChild(Node(3)
                        .addChild(Node(7)
                                .addChild(10).addChild(11)));
    }

    public static class NodeFactory {
        public static Node Node(final int id) {
            return new Node<Integer>(id);
        }
    }


    public static class Node<T> {
        private final T id;
        private final List<Node<T>> children;

        public Node(T id) {
            this(id, new LinkedList<Node<T>>());
        }

        public Node(T id, List<Node<T>> children) {
            if (children == null) {
                throw new IllegalArgumentException();
            }
            this.id = id;
            this.children = children;
        }

        public Node<T> addChild(T id) {
            children.add(new Node<T>(id));
            return this;
        }

        public Node<T> addChild(Node<T> child) {
            children.add(child);
            return this;
        }

        @Override
        public String toString() {
            ToStringBuilder builder = new ToStringBuilder(this).append(id.toString());
            if (!children.isEmpty()) {
                builder.append(children);
            }
            return builder.toString();
        }
    }
}
