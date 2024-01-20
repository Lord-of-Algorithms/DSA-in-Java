package binarytree.bst.red_black;

import binarytree.INode;

/**
 * Represents a node in a Red-Black Tree.
 * Each node contains a key, links to left and right children, and a parent node.
 * The node also has a color (red or black) and a flag indicating if it's a sentinel node.
 */
public class RbNode implements INode {

    int key;
    RbNode left;
    RbNode right;
    RbNode parent;
    private boolean isBlack;
    private boolean isSentinel;

    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";

    /**
     * Constructor for creating a regular RbNode.
     * Initializes the node with a given key and sets its children and parent to the sentinel node.
     *
     * @param key      The key to be stored in the node.
     * @param sentinel The sentinel node to be used as the initial children and parent.
     */
    RbNode(int key, RbNode sentinel) {
        this.key = key;
        left = sentinel;
        right = sentinel;
        parent = sentinel;
    }

    /**
     * Private constructor used for creating a sentinel node.
     */
    private RbNode() {
        // Sentinel node constructor
    }

    /**
     * Factory method to create a sentinel node.
     * Sentinel nodes are used to represent external null leaves and the root's parent in the Red-Black Tree.
     * Sentinel nodes are always black.
     *
     * @return A new sentinel node.
     */
    static RbNode createSentinelNode() {
        var node = new RbNode();
        node.isSentinel = true;
        node.setBlack();
        return node;
    }

    /**
     * Checks if the node is red.
     *
     * @return True if the node is red, false if the node is black.
     */
    boolean isRed() {
        return !isBlack;
    }

    /**
     * Checks if the node is black.
     *
     * @return True if the node is black, false if the node is red.
     */
    boolean isBlack() {
        return isBlack;
    }

    /**
     * Sets the node's color to black.
     */
    void setBlack() {
        isBlack = true;
    }

    /**
     * Sets the node's color to red.
     */
    void setRed() {
        isBlack = false;
    }

    @Override
    public int getKey() {
        return key;
    }

    @Override
    public INode getLeft() {
        if (left != null && !left.isSentinel) {
            return left;
        } else {
            return null;
        }
    }

    @Override
    public INode getRight() {
        if (right != null && !right.isSentinel) {
            return right;
        } else {
            return null;
        }
    }

    /**
     * Visits this node during traversal.
     * In this implementation, visiting a node prints its key to the standard output.
     */
    @Override
    public void visit() {
        System.out.print(key + " ");
    }

    /**
     * Returns a string representation of the node's key.
     * For visual distinction, red nodes are displayed in red color using ANSI color codes,
     * while black nodes are shown in the default console color.
     * Note: ANSI color codes might not render correctly in some environments.
     *
     * @return The colored (if red) or default string representation of the node's key.
     */
    @Override
    public String toString() {
        if (isRed()) {
            return ANSI_RED + key + ANSI_RESET;
        }
        return "" + key;
    }
}
