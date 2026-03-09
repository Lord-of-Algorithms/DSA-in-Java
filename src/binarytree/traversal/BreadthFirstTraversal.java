package binarytree.traversal;

import binarytree.TreeNode;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Class for performing breadth-first traversal on a binary tree.
 */
public class BreadthFirstTraversal implements Traversal {

    @Override
    public void traverse(TreeNode root) {
        if (root != null) {
            Queue<TreeNode> queue = new ArrayDeque<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                TreeNode node = queue.remove();
                node.visit();
                if (node.getLeft() != null) {
                    queue.add(node.getLeft());
                }

                if (node.getRight() != null) {
                    queue.add(node.getRight());
                }
            }
        }
    }
}
