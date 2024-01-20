package binarytree.bst.standard;

import binarytree.INode;

/**
 * Represents a node in a binary tree, implementing the INode interface.
 */
public class Node implements INode {
    public int key;
    public Node left;
    public Node right;

    Node(int key) {
        this.key = key;
    }

    @Override
    public int getKey() {
        return key;
    }

    @Override
    public INode getLeft() {
        return left;
    }

    @Override
    public INode getRight() {
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
