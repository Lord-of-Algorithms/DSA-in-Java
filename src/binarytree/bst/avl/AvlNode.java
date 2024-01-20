package binarytree.bst.avl;

import binarytree.INode;

/**
 * Represents a node in an AVL tree, implementing the INode interface.
 */
public class AvlNode implements INode {

    int key;
    AvlNode left;
    AvlNode right;
    int height; // Height of the node

    AvlNode(int key) {
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
