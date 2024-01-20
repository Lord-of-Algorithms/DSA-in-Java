package binarytree.traversal;

import binarytree.INode;

/**
 * Interface for implementing different traversal strategies on a binary tree.
 */
public interface ITraversal {
    /**
     * Performs traversal of a binary tree.
     * @param root The root node of the tree to traverse.
     */
    void traverse(INode root);
}
