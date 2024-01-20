package binarytree.traversal;

import binarytree.INode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Class for performing post-order traversal on a binary tree.
 */
public class PostOrderTraversal implements ITraversal {

    private final TraversalNature traversalNature;

    public PostOrderTraversal(TraversalNature traversalNature) {
        this.traversalNature = traversalNature;
    }

    @Override
    public void traverse(INode root) {
        if (traversalNature == TraversalNature.Iterative) {
            iterativePostOrderTraverse(root);
        } else {
            recursivePostOrderTraverse(root);
        }
    }

    /**
     * Iteratively traverses a binary tree in an post-order manner.
     */
    private static void iterativePostOrderTraverse(INode root) {
        if (root == null) {
            return;
        }
        Deque<INode> stack1 = new ArrayDeque<>();
        Deque<INode> stack2 = new ArrayDeque<>();

        stack1.push(root);

        while (!stack1.isEmpty()) {

            INode node = stack1.pop();
            stack2.push(node);

            if (node.getLeft() != null) {
                stack1.push(node.getLeft());
            }
            if (node.getRight() != null) {
                stack1.push(node.getRight());
            }
        }
        while (!stack2.isEmpty()) {
            stack2.pop().visit();
        }
    }

    /**
     * Recursively traverses a binary tree in an post-order manner.
     */
    private static void recursivePostOrderTraverse(INode root) {
        if (root != null) {
            // Recursively traverse the left subtree
            recursivePostOrderTraverse(root.getLeft());

            // Recursively traverse the right subtree
            recursivePostOrderTraverse(root.getRight());

            root.visit();
        }
    }
}
