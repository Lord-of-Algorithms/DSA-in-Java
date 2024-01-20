package binarytree;

/**
 * Interface defining the basic structure and functionality of a node in a binary tree.
 */
public interface INode {

    /**
     * Gets the key value of this node.
     */
    int getKey();

    /**
     * Gets the left child of this node.
     *
     * @return The left child node.
     */
    INode getLeft();

    /**
     * Gets the right child of this node.
     *
     * @return The right child node.
     */
    INode getRight();

    /**
     * Processes or visits the current node.
     */
    void visit();
}
