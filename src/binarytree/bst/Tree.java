package binarytree.bst;

import binarytree.TreeNode;

/**
 * Interface for a binary search tree.
 */
public interface Tree {

    /**
     * Retrieves the root node of the tree.
     */
    TreeNode getRoot();

    /**
     * Inserts a new node with the specified key into the tree.
     *
     * @param key The key of the new node to be inserted.
     */
    void insert(int key);

    /**
     * Deletes the node with the specified key from the tree.
     *
     * @param key The key of the node to be deleted.
     */
    void delete(int key);

    /**
     * Searches for a node by its key.
     *
     * @param key The key to search for.
     * @return The node with the specified key, or null if not found.
     */
    TreeNode search(int key);
}
