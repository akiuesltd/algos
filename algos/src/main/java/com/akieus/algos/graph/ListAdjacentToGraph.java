package com.akieus.algos.graph;

import com.akieus.algos.Tree;

import java.util.*;

/**
 * Created by aks on 09/08/15.
 */
public class ListAdjacentToGraph {

    public static void main(String[] args) {
        int[][] nodes = new int[][]{{1, 2, 3}, {2, 3, 4, 5}, {3, 2, 6}, {4}, {5}, {6}};
        printGraphBFS(toTree(nodes));
    }

    private static void printGraphBFS(TreeNode node) {
        Set<TreeNode> bfsTree = traverseGraphBFS(node);
        print(bfsTree);
    }

    private static Set<TreeNode> traverseGraphBFS(TreeNode root) {
        Set<TreeNode> visited = new LinkedHashSet<>();
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while(!queue.isEmpty()) {
            TreeNode next = queue.removeFirst();
            visited.add(next);
            for (TreeNode child : next.getChildren()) {
                if (!visited.contains(child)) {
                    queue.addLast(child);
                }
            }
        }

        return visited;
    }

    public static TreeNode toTree(int[][] nodes) {
        ArrayList<TreeNode> treeNodes = new ArrayList<>();
        // first create all nodes
        for (int[] nodeAndChildren : nodes) {
            int node = nodeAndChildren[0];
            treeNodes.add(new TreeNode(node));
        }

        // now set up children
        for (int i = 0; i < nodes.length; i++) {
            int[] nodeAndChildren = nodes[i];
            TreeNode parent = treeNodes.get(i);
            for (int j = 1; j < nodeAndChildren.length; j++) {
                TreeNode child = treeNodes.get(nodeAndChildren[j] - 1);
                parent.addChild(child);
            }
        }

//        print(treeNodes);
        return treeNodes.get(0);
    }

    private static void print(Collection<TreeNode> nodes) {
        for (TreeNode node : nodes) {
            System.out.println(node.getId() + ", ");
        }
    }
}
