package com.akieus.algos.graph;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.LinkedList;
import java.util.List;

/**
 * @author aks
 * @since 09/08/15.
 */
public class TreeNode {
    private final int id;
    private final List<TreeNode> children;

    public TreeNode(int id) {
        this(id, new LinkedList<TreeNode>());
    }

    public TreeNode(int id, List<TreeNode> children) {
        if (children == null) {
            throw new IllegalArgumentException();
        }
        this.id = id;
        this.children = children;
    }

    public TreeNode addChild(int id) {
        children.add(new TreeNode(id));
        return this;
    }

    public TreeNode addChild(TreeNode child) {
        children.add(child);
        return this;
    }

    public int getId() {
        return id;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof TreeNode)) {
            return false;
        }
        return this.id == ((TreeNode) other).id;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder().append(id);
        if (!children.isEmpty()) {
            builder.append(":> ");
            builder.append(children).append(", ");
        }
        if (builder.lastIndexOf(", ") != -1) {
            return builder.substring(0, builder.lastIndexOf(", ")).toString();
        } else {
            return builder.toString();
        }
    }
}
