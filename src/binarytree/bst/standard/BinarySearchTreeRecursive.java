package binarytree.bst.standard;

import binarytree.INode;
import binarytree.bst.BstUtils;
import binarytree.bst.ITree;

/**
 * Implementation of a binary search tree with recursive methods.
 */
public class BinarySearchTreeRecursive implements ITree {

    private Node root;

    @Override
    public INode getRoot() {
        return root;
    }

    @Override
    public void insert(int key) {
        root = insertRecursive(root, key);
    }

    /**
     * Helper method to recursively insert a new node.
     *
     * @param current The root of the subtree.
     * @param key     The key of the new node to be inserted.
     * @return The modified subtree with the new node inserted.
     */
    private Node insertRecursive(Node current, int key) {
        // Base case: If the current node is null, a new node is created and returned.
        if (current == null) {
            return new Node(key);
        }

        // If the key to insert is less than the current node's key, insert in the left subtree.
        if (key < current.key) {
            current.left = insertRecursive(current.left, key);
        }
        // If the key to insert is greater than the current node's key, insert in the right subtree.
        else if (key > current.key) {
            current.right = insertRecursive(current.right, key);
        }
        // If the key already exists in the BST, we do not insert duplicates.
        else {
            BstUtils.handleDuplicateKey(key);
            // Return the unchanged root (current) of the subtree.
        }
        return current;
    }

    @Override
    public void delete(int key) {
        root = deleteRecursive(root, key);
    }

    /**
     * Helper method to recursively find and delete a node.
     *
     * @param current The root of the subtree where the node with the specified key will be deleted.
     * @param key     The key of the node to be deleted.
     * @return The modified subtree after deletion.
     */
    private Node deleteRecursive(Node current, int key) {
        if (current == null) return null;

        // Traverse the left subtree if the key to delete is smaller than the current node's key
        if (key < current.key)
            current.left = deleteRecursive(current.left, key);
        else if (key > current.key)
            // Traverse the right subtree if the key to delete is greater than the current node's key
            current.right = deleteRecursive(current.right, key);
        else {
            // Case 1: Node with only one child or no child
            if (current.left == null)
                return current.right;
            else if (current.right == null)
                return current.left;

            // Case 2: Node with two children
            // Replace node's key with the minimum value from its right subtree
            current.key = BstUtils.minValue(current.right);
            // Delete the in-order successor (which has the minimum value)
            current.right = deleteRecursive(current.right, current.key);
        }

        return current;
    }

    @Override
    public INode search(int key) {
        return BstUtils.searchRecursive(root, key);
    }
}
