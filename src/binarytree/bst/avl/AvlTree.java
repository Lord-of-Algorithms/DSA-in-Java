package binarytree.bst.avl;

import binarytree.INode;
import binarytree.bst.BstUtils;
import binarytree.bst.ITree;

/**
 * Represents an AVL tree, a self-balancing binary search tree.
 */
public class AvlTree implements ITree {

    private AvlNode root; // Root of the AVL tree

    @Override
    public INode getRoot() {
        return root;
    }

    @Override
    public void insert(int key) {
        root = insertRecursive(root, key);
    }

    /**
     * Helper method to recursively insert a new key into the AVL tree.
     *
     * @param current The root node of the current subtree.
     * @param key     The key to insert.
     * @return The new root of the subtree after insertion.
     */
    private AvlNode insertRecursive(AvlNode current, int key) {

        if (current == null)
            return new AvlNode(key);

        if (key < current.key)
            current.left = insertRecursive(current.left, key);
        else if (key > current.key)
            current.right = insertRecursive(current.right, key);
        else {
            BstUtils.handleDuplicateKey(key);
            return current;
        }

        // Update height and calculate balance factor
        updateHeight(current);
        int balance = getBalance(current);

        // Single Right Rotation
        if (balance > 1 && key < current.left.key)
            return rightRotate(current);

        // Single Left Rotation
        if (balance < -1 && key > current.right.key)
            return leftRotate(current);

        // Left-Right Rotation
        if (balance > 1 && key > current.left.key) {
            current.left = leftRotate(current.left);
            return rightRotate(current);
        }

        // Right-Left Rotation
        if (balance < -1 && key < current.right.key) {
            current.right = rightRotate(current.right);
            return leftRotate(current);
        }

        return current;
    }

    /**
     * Calculates the height of a given node in the AVL tree.
     * The height of a node is defined as the number of edges from the node to the deepest leaf.
     * A leaf node has a height of 0, and an empty tree (or null node) has a height of -1.
     *
     * @param node The node whose height is to be calculated.
     * @return The height of the node, or -1 if the node is null.
     */
    private static int getHeight(AvlNode node) {
        if (node == null)
            return -1; // An empty tree or null node has a height of -1
        return node.height;
    }

    /**
     * Calculates the balance factor of a given node in the AVL tree.
     * The balance factor is defined as the difference in height between the left and right subtrees.
     * A balance factor greater than 1 indicates left-heavy imbalance,
     * and a balance factor less than -1 indicates right-heavy imbalance.
     *
     * @param node The node for which the balance factor is to be calculated.
     * @return The balance factor of the node. If the node is null, returns 0.
     */
    private static int getBalance(AvlNode node) {
        if (node == null)
            return 0;
        return getHeight(node.left) - getHeight(node.right);
    }

    /**
     * Performs a right rotation on the given node.
     * This rotation is used to correct the left-heavy imbalance in the AVL tree.
     * In a right rotation, the left child of the rotated node becomes the new root
     * of the subtree, and the original node becomes the right child of this new root.
     *
     * @param rotatedNode The node around which the right rotation is to be performed.
     * @return The new root of the subtree after the rotation.
     */
    private static AvlNode rightRotate(AvlNode rotatedNode) {
        AvlNode newRoot = rotatedNode.left;
        AvlNode reParentedNode = newRoot.right;

        // Perform rotation
        newRoot.right = rotatedNode;
        rotatedNode.left = reParentedNode;

        // Update heights of the rotated nodes
        updateHeight(rotatedNode);
        updateHeight(newRoot);

        return newRoot;
    }

    private static void updateHeight(AvlNode node) {
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    /**
     * Performs a left rotation on the given node.
     * This rotation is used to correct the right-heavy imbalance in the AVL tree.
     * In a left rotation, the right child of the rotated node becomes the new root
     * of the subtree, and the original node becomes the left child of this new root.
     *
     * @param rotatedNode The node around which the left rotation is to be performed.
     * @return The new root of the subtree after the rotation.
     */
    private static AvlNode leftRotate(AvlNode rotatedNode) {
        AvlNode newRoot = rotatedNode.right;
        AvlNode reParentedNode = newRoot.left;

        // Perform rotation
        newRoot.left = rotatedNode;
        rotatedNode.right = reParentedNode;

        // Update heights of the rotated nodes
        updateHeight(rotatedNode);
        updateHeight(newRoot);

        return newRoot;
    }

    @Override
    public void delete(int key) {
        root = deleteRecursive(root, key);
    }

    /**
     * Helper method to recursively delete a key from the AVL tree.
     *
     * @param current The root node of the current subtree.
     * @param key     The key to delete.
     * @return The new root of the subtree after deletion.
     */
    private AvlNode deleteRecursive(AvlNode current, int key) {
        // Standard deletion in a binary search tree
        if (current == null)
            return null;

        if (key < current.key)
            current.left = deleteRecursive(current.left, key);
        else if (key > current.key)
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

        // Update height and calculate balance factor
        updateHeight(current);
        int balance = getBalance(current);

        // Single Right rotation
        if (balance > 1 && getBalance(current.left) >= 0)
            return rightRotate(current);

        // Left-right rotation
        if (balance > 1 && getBalance(current.left) < 0) {
            current.left = leftRotate(current.left);
            return rightRotate(current);
        }

        // Single Left rotation
        if (balance < -1 && getBalance(current.right) <= 0)
            return leftRotate(current);

        // Right-left rotation
        if (balance < -1 && getBalance(current.right) > 0) {
            current.right = rightRotate(current.right);
            return leftRotate(current);
        }

        return current;
    }

    @Override
    public INode search(int key) {
        return BstUtils.searchRecursive(root, key);
    }
}
