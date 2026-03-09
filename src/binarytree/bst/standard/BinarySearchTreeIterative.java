package binarytree.bst.standard;

import binarytree.TreeNode;
import binarytree.bst.BstUtils;
import binarytree.bst.Tree;

/**
 * Implementation of a binary search tree with iterative methods.
 */
public class BinarySearchTreeIterative implements Tree {

    private BstNode root;

    @Override
    public TreeNode getRoot() {
        return root;
    }

    @Override
    public void insert(int key) {
        BstNode newNode = new BstNode(key);
        // Handle the case of an empty tree.
        if (root == null) {
            root = newNode;
            return;
        }

        BstNode current = root;
        BstNode parent = null;

        // Traverse the tree to find the appropriate insertion point.
        while (current != null) {
            parent = current;
            if (key < current.key) {
                current = current.left;
            } else if (key > current.key) {
                current = current.right;
            } else {
                // Duplicate key found.
                BstUtils.handleDuplicateKey(key);
                return;
            }
        }

        // Attaching the new node to its parent.
        if (key < parent.key) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
    }

    @Override
    public void delete(int key) {
        if (root == null) {
            System.out.println("The tree is empty.");
            return;
        }

        BstNode nodeToDelete = root;
        BstNode parentNode = null;

        // Find the node to delete and its parent
        while (nodeToDelete != null && nodeToDelete.key != key) {
            parentNode = nodeToDelete;
            if (key < nodeToDelete.key) {
                nodeToDelete = nodeToDelete.left;
            } else {
                nodeToDelete = nodeToDelete.right;
            }
        }

        if (nodeToDelete == null) {
            System.out.println("Node with key " + key + " not found.");
            return;
        }

        // Check if nodeToDelete is the root
        boolean isRootNode = nodeToDelete == root;
        boolean isLeftChild = parentNode != null && nodeToDelete.key < parentNode.key;

        // Case 1: Node is a leaf (no children)
        if (nodeToDelete.left == null && nodeToDelete.right == null) {
            if (isRootNode) {
                root = null;
            } else if (isLeftChild) {
                parentNode.left = null;
            } else {
                parentNode.right = null;
            }
        }
        // Case 2: Node has only one child
        else if (nodeToDelete.left == null || nodeToDelete.right == null) {
            BstNode childNode = (nodeToDelete.left != null) ? nodeToDelete.left : nodeToDelete.right;

            if (isRootNode) {
                root = childNode;
            } else if (isLeftChild) {
                parentNode.left = childNode;
            } else {
                parentNode.right = childNode;
            }
        }
        // Case 3: Node has two children
        else {
            // Find the in-order successor (smallest in the right subtree)
            BstNode successor = nodeToDelete.right;
            BstNode successorParent = nodeToDelete;
            while (successor.left != null) {
                successorParent = successor;
                successor = successor.left;
            }

            // If the successor is not the direct right child of nodeToDelete,
            // then handle the re-linking of the successor and its parent
            if (successor != nodeToDelete.right) {
                // Re-link the left child of the successor's parent to the successor's right child
                successorParent.left = successor.right;

                // Link the right child of the nodeToDelete to the successor
                successor.right = nodeToDelete.right;
            }

            successor.left = nodeToDelete.left;

            // Replace nodeToDelete with successor in its parent
            if (isRootNode) {
                root = successor;
            } else if (isLeftChild) {
                parentNode.left = successor;
            } else {
                parentNode.right = successor;
            }
        }
    }

    @Override
    public TreeNode search(int key) {
        BstNode current = root;
        while (current != null) {
            if (key == current.key) {
                return current; // Node found
            }
            current = key < current.key ? current.left : current.right;
        }
        return null; // Key not found
    }
}
