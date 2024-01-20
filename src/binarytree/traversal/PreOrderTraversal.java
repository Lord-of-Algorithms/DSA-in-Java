package binarytree.traversal;

import binarytree.INode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Class for performing pre-order traversal on a binary tree.
 */
public class PreOrderTraversal implements ITraversal {

    private final TraversalNature traversalNature;

    public PreOrderTraversal(TraversalNature traversalNature) {
        this.traversalNature = traversalNature;
    }

    @Override
    public void traverse(INode root) {
        if (traversalNature == TraversalNature.Iterative) {
            iterativePreOrderTraverse(root);
        } else {
            recursivePreOrderTraverse(root);
        }
    }

    /**
     * Iteratively traverses a binary tree in an pre-order manner.
     */
    private static void iterativePreOrderTraverse(INode root) {
        if (root == null) {
            return;
        }

        Deque<INode> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            // Pop the top item from stack
            INode node = stack.pop();

            node.visit();

            // Push right and left children of the
            // popped node to stack
            if (node.getRight() != null) {
                stack.push(node.getRight());
            }
            if (node.getLeft() != null) {
                stack.push(node.getLeft());
            }
        }
    }

    /**
     * Recursively traverses a binary tree in an pre-order manner.
     */
    private static void recursivePreOrderTraverse(INode node) {
        if (node == null) {
            return;
        }

        node.visit();

        // Recursively traverse the left subtree
        recursivePreOrderTraverse(node.getLeft());

        // Recursively traverse the right subtree
        recursivePreOrderTraverse(node.getRight());
    }
}
