package binarytree.traversal;

import binarytree.TreeNode;

/**
 * Interface for implementing different traversal strategies on a binary tree.
 */
public interface Traversal {
    /**
     * Performs traversal of a binary tree.
     * @param root The root node of the tree to traverse.
     */
    void traverse(TreeNode root);
}
