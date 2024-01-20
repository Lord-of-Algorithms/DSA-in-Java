package binarytree.traversal;

import binarytree.INode;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Class for performing breadth-first traversal on a binary tree.
 */
public class BreadthFirstTraversal implements ITraversal {

    @Override
    public void traverse(INode root) {
        if (root != null) {
            Queue<INode> queue = new ArrayDeque<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                INode node = queue.remove();
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
