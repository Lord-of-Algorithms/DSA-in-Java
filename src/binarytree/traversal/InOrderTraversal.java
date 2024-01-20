package binarytree.traversal;

import binarytree.INode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Class for performing in-order traversal on a binary tree.
 */
public class InOrderTraversal implements ITraversal {

    private final TraversalNature traversalNature;

    public InOrderTraversal(TraversalNature traversalNature) {
        this.traversalNature = traversalNature;
    }

    @Override
    public void traverse(INode root) {
        if (traversalNature == TraversalNature.Iterative) {
            iterativeInOrderTraverse(root);
        } else {
            recursiveInOrderTraverse(root);
        }
    }

    /**
     * Iteratively traverses a binary tree in an in-order manner.
     */
    private static void iterativeInOrderTraverse(INode root) {
        if (root == null) {
            return;
        }
        Deque<INode> stack = new ArrayDeque<>();
        INode node = root;

        while (!stack.isEmpty() || node != null) {
            if (node != null) {
                // Push the node into the stack a
                // and move to its left child
                stack.push(node);
                node = node.getLeft();
            } else {
                // If the node is null, pop an element
                // from the stack, visit it and move
                // to its right child
                node = stack.pop();
                node.visit();
                node = node.getRight();
            }
        }
    }

    /**
     * Recursively traverses a binary tree in an in-order manner.
     */
    private static void recursiveInOrderTraverse(INode node) {
        if (node == null) {
            return;
        }

        // Recursively traverse the left subtree
        recursiveInOrderTraverse(node.getLeft());

        node.visit();

        // Recursively traverse the right subtree
        recursiveInOrderTraverse(node.getRight());
    }
}
