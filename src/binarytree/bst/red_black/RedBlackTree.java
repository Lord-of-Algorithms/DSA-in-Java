package binarytree.bst.red_black;

import binarytree.INode;
import binarytree.bst.BstUtils;
import binarytree.bst.ITree;

/**
 * Represents a Red-Black Tree, a self-balancing binary search tree.
 * In a Red-Black Tree, each node is colored either red or black and the tree
 * maintains several balancing properties to ensure operations have logarithmic time complexity.
 */
public class RedBlackTree implements ITree {

    private RbNode root;
    private final RbNode NIL;

    /**
     * Constructor for creating a Red-Black Tree.
     * Initializes the tree with a sentinel node representing NIL.
     */
    public RedBlackTree() {
        NIL = RbNode.createSentinelNode();
        root = NIL;
    }

    @Override
    public INode getRoot() {
        return root;
    }

    /**
     * Inserts a new key into the Red-Black Tree.
     * This method ensures that the tree maintains its properties after insertion.
     *
     * @param key The key to be inserted.
     */
    @Override
    public void insert(int key) {
        RbNode newNode = new RbNode(key, NIL);
        if (root == NIL) {
            root = newNode;
            // Root is always black
            root.setBlack();
            return;
        }

        RbNode current = root;
        RbNode parent = NIL;

        // Traverse the tree to find the appropriate insertion point.
        while (current != NIL) {
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

        newNode.parent = parent;
        // Attaching the new node to its parent.
        if (key < parent.key) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        fixInsert(newNode);
    }

    /**
     * Fixes the Red-Black Tree after an insertion.
     * This method adjusts colors and performs rotations to maintain the tree's properties.
     *
     * @param current The newly inserted node.
     */
    private void fixInsert(RbNode current) {
        RbNode parent = current.parent;
        while (parent.isRed()) {
            RbNode uncle;
            RbNode grandParent = parent.parent;
            if (parent == grandParent.right) {
                uncle = grandParent.left;
                // Case 1: The uncle is red
                if (uncle.isRed()) {
                    uncle.setBlack();
                    parent.setBlack();
                    grandParent.setRed();
                    current = grandParent;

                    // Reassign parent after changing 'current'
                    parent = current.parent;
                } else {
                    // Case 2: The uncle is black, the new node is a left child
                    // and its parent is a right child
                    if (current == parent.left) {
                        current = parent;
                        rotateRight(current);

                        // Reassign parent and grandparent after changing 'current'
                        parent = current.parent;
                        grandParent = parent.parent;
                    }
                    // Case 3: The uncle is black, the new node and
                    // its parent are both right children
                    parent.setBlack();
                    grandParent.setRed();
                    rotateLeft(grandParent);
                }
            } else {
                uncle = grandParent.right;

                // Case 1: The uncle is red
                if (uncle.isRed()) {
                    uncle.setBlack();
                    parent.setBlack();
                    grandParent.setRed();
                    current = grandParent;

                    // Reassign parent after changing 'current'
                    parent = current.parent;
                } else {
                    // Case 2: The uncle is black, the new node is a right child
                    // and its parent is a left child
                    if (current == parent.right) {
                        current = parent;
                        rotateLeft(current);

                        // Reassign parent and grandparent after changing 'current'
                        parent = current.parent;
                        grandParent = parent.parent;
                    }
                    // Case 3: The uncle is black, the new node and
                    // its parent are both left children
                    parent.setBlack();
                    grandParent.setRed();
                    rotateRight(grandParent);
                }
            }
            if (current == root) {
                break;
            }
        }
        // Keep root always black
        root.setBlack();
    }

    /**
     * Performs a left rotation on a given node.
     * Left rotation is used to rebalance the tree during insertion and deletion operations.
     *
     * @param rotatedNode The node around which the left rotation will be performed.
     */
    private void rotateLeft(RbNode rotatedNode) {
        RbNode newRoot = rotatedNode.right;
        RbNode reParentedNode = newRoot.left;
        // Connect the re-parented node and rotatedNode
        rotatedNode.right = reParentedNode;
        if (reParentedNode != NIL) {
            reParentedNode.parent = rotatedNode;
        }
        newRoot.parent = rotatedNode.parent;
        // Attach the new root of subtree to the parent
        if (rotatedNode.parent == NIL) {
            root = newRoot;
        } else if (rotatedNode == rotatedNode.parent.left) {
            rotatedNode.parent.left = newRoot;
        } else {
            rotatedNode.parent.right = newRoot;
        }
        // Connect the rotatedNode and the new root of subtree
        newRoot.left = rotatedNode;
        rotatedNode.parent = newRoot;
    }

    /**
     * Performs a right rotation on a given node.
     * Right rotation is used to rebalance the tree during insertion and deletion operations.
     *
     * @param rotatedNode The node around which the right rotation will be performed.
     */
    private void rotateRight(RbNode rotatedNode) {
        RbNode newRoot = rotatedNode.left;
        RbNode reParentedNode = newRoot.right;
        // Connect the re-parented node and rotatedNode
        rotatedNode.left = reParentedNode;
        if (reParentedNode != NIL) {
            reParentedNode.parent = rotatedNode;
        }
        newRoot.parent = rotatedNode.parent;
        // Attach the new root of subtree to the parent
        if (rotatedNode.parent == NIL) {
            root = newRoot;
        } else if (rotatedNode == rotatedNode.parent.right) {
            rotatedNode.parent.right = newRoot;
        } else {
            rotatedNode.parent.left = newRoot;
        }
        // Connect the rotatedNode and the new root of subtree
        newRoot.right = rotatedNode;
        rotatedNode.parent = newRoot;
    }

    /**
     * Deletes a key from the Red-Black Tree.
     * This method also ensures that the tree maintains its properties after deletion.
     *
     * @param key The key to be deleted.
     */
    @Override
    public void delete(int key) {
        if (root == NIL) {
            System.out.println("The tree is empty.");
            return;
        }

        RbNode nodeToDelete = root;

        // Find the node to delete
        while (nodeToDelete != NIL && nodeToDelete.key != key) {
            if (key < nodeToDelete.key) {
                nodeToDelete = nodeToDelete.left;
            } else {
                nodeToDelete = nodeToDelete.right;
            }
        }

        if (nodeToDelete == NIL) {
            System.out.println("Node with key " + key + " not found.");
            return;
        }

        // Handle node deletion based on the number of children
        // Node with zero or one child
        if (nodeToDelete.left == NIL || nodeToDelete.right == NIL) {

            boolean isDeletingNodeBlack = nodeToDelete.isBlack();
            RbNode replacerNode = NIL;

            if (nodeToDelete.left == NIL) {
                replacerNode = nodeToDelete.right;
                ReplaceSubTree(nodeToDelete, replacerNode);
            } else if (nodeToDelete.right == NIL) {
                replacerNode = nodeToDelete.left;
                ReplaceSubTree(nodeToDelete, replacerNode);
            }

            if (isDeletingNodeBlack) {
                fixDelete(replacerNode);
            }
        }
        // Node with two children
        else {
            // Find the in-order successor (the leftmost node in the right subtree)
            RbNode successor = nodeToDelete.right;
            while (successor.left != NIL) {
                successor = successor.left;
            }

            // Remember the color of the successor and keep the link to its child
            boolean isInitiallySuccessorBlack = successor.isBlack();
            RbNode successorChild = successor.right;

            if (successor.parent != nodeToDelete) // Successor is not a direct child of the deleting node
            {
                // Replace the successor with its child
                ReplaceSubTree(successor, successor.right);
                successor.right = nodeToDelete.right;
            }
            // When successor.right is NIL, this line is ensuring
            // that the NIL node's parent reference is updated.
            successor.right.parent = successor;

            // Replace the node to be deleted with successor
            ReplaceSubTree(nodeToDelete, successor);
            successor.left = nodeToDelete.left;
            successor.left.parent = successor;

            transferColor(nodeToDelete, successor);

            if (isInitiallySuccessorBlack) {
                fixDelete(successorChild);
            }
        }
    }

    /**
     * Replaces one subtree rooted at 'targetNode' with another subtree rooted at 'replacerNode'.
     * This method is used to replace a node with its child.
     *
     * @param targetNode   The root of the subtree that will be replaced.
     * @param replacerNode The root of the subtree that replaces the target subtree.
     */
    private void ReplaceSubTree(RbNode targetNode, RbNode replacerNode) {
        if (targetNode.parent == NIL) {
            root = replacerNode;
        } else if (targetNode == targetNode.parent.left) {
            targetNode.parent.left = replacerNode;
        } else {
            targetNode.parent.right = replacerNode;
        }

        replacerNode.parent = targetNode.parent;
    }

    /**
     * Transfers the color from one node to another in a Red-Black Tree.
     * If sourceNode is red, targetNode is set to red; if sourceNode is black, targetNode is set to black.
     *
     * @param sourceNode The node from which the color is copied.
     * @param targetNode The node to which the color is applied.
     */
    private void transferColor(RbNode sourceNode, RbNode targetNode) {
        if (sourceNode.isRed()) {
            targetNode.setRed();
        } else {
            targetNode.setBlack();
        }
    }

    /**
     * Restores the Red-Black Tree properties after the deletion of a node.
     * This method addresses the 'double black' problem that arises when a black node is deleted
     * or replaced by a black child. It rebalances the tree and ensures that the Red-Black Tree
     * properties are maintained.
     *
     * @param current The node starting from where the tree needs to be fixed.
     */
    private void fixDelete(RbNode current) {
        while (current != root && current.isBlack()) {
            RbNode sibling;

            // The double black node 'current' is a left child
            if (current == current.parent.left) {

                // Sibling is a right child
                sibling = current.parent.right;
                if (sibling.isRed()) {
                    // Case 1: The sibling is red
                    sibling.setBlack();
                    current.parent.setRed();
                    rotateLeft(current.parent);
                    sibling = current.parent.right; // Update the sibling after rotation
                }

                if (sibling.left.isBlack() && sibling.right.isBlack()) {
                    // Case 2: The sibling is black and has two black children
                    sibling.setRed();
                    current = current.parent; // Propagate the double black problem up
                } else {
                    if (sibling.right.isBlack()) {
                        // Case 3: The sibling is black and has one red child (left)
                        // and one black child (right)
                        sibling.left.setBlack();
                        sibling.setRed();
                        rotateRight(sibling);
                        sibling = current.parent.right; // Update the sibling after rotation
                    }

                    // Case 4: The sibling is black with a right red child
                    transferColor(current.parent, sibling);
                    current.parent.setBlack();
                    sibling.right.setBlack();
                    rotateLeft(current.parent);

                    current = root; // Resolve the double black and exit the loop
                }
            }
            // Mirror operations if the double black node 'current' is a right child
            else {

                // Sibling is a left child
                sibling = current.parent.left;
                if (sibling.isRed()) {
                    // Case 1: The sibling is red
                    sibling.setBlack();
                    current.parent.setRed();
                    rotateRight(current.parent);
                    sibling = current.parent.left; // Update the sibling after rotation
                }

                if (sibling.right.isBlack() && sibling.left.isBlack()) {
                    // Case 2: The sibling is black and has two black children
                    sibling.setRed();
                    current = current.parent; // Propagate the double black problem up
                } else {
                    if (sibling.left.isBlack()) {
                        // Case 3: The sibling is black and has one red child (right)
                        // and one black child (left)
                        sibling.right.setBlack();
                        sibling.setRed();
                        rotateLeft(sibling);
                        sibling = current.parent.left; // Update the sibling after rotation
                    }

                    // Case 4: The sibling is black with a left red child
                    transferColor(current.parent, sibling);
                    current.parent.setBlack();
                    sibling.left.setBlack();
                    rotateRight(current.parent);

                    current = root; // Resolve the double black and exit the loop
                }
            }
        }

        current.setBlack(); // Ensures the root of the tree is always black
    }

    /**
     * Searches for a key in the Red-Black Tree.
     * This method uses a recursive binary search tree search.
     *
     * @param key The key to be searched for.
     * @return The node containing the key, or null if the key isn't found.
     */
    @Override
    public INode search(int key) {
        return BstUtils.searchRecursive(root, key);
    }
}
