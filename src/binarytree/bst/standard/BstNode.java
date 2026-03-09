package binarytree.bst.standard;

import binarytree.TreeNode;

/**
 * Represents a node in a binary tree, implementing the INode interface.
 */
public class BstNode implements TreeNode {
    public int key;
    public BstNode left;
    public BstNode right;

    BstNode(int key) {
        this.key = key;
    }

    @Override
    public int getKey() {
        return key;
    }

    @Override
    public TreeNode getLeft() {
        return left;
    }

    @Override
    public TreeNode getRight() {
        return right;
    }

    /**
     * Visits this node. In this implementation, it prints the node's key to the standard output.
     */
    @Override
    public void visit() {
        System.out.print(key + " ");
    }

    @Override
    public String toString() {
        return "" + key;
    }
}
