package binarytree.bst;

import binarytree.INode;

/**
 * Helper class containing common methods for binary search trees.
 */
public class BstUtils {
    private BstUtils() {
        // we use 'private' access modifier to prevent instantiating
    }

    /**
     * Get the in-order successor's key (smallest in the right subtree)
     */
    public static int minValue(INode root) {
        int minValue = root.getKey();
        while (root.getLeft() != null) {
            minValue = root.getLeft().getKey();
            root = root.getLeft();
        }
        return minValue;
    }

    /**
     * Helper method to recursively search for a node.
     *
     * @param current The root of the subtree where the node with the specified key will be searched.
     * @param key     The key to search for.
     * @return The node with the specified key, or null if not found.
     */
    public static INode searchRecursive(INode current, int key) {
        if (current == null) {
            return null; // Base case: key not found or tree is empty
        }
        if (key == current.getKey()) {
            return current; // Node found
        }
        return key < current.getKey() ? searchRecursive(current.getLeft(), key) : searchRecursive(current.getRight(), key);
    }

    /**
     * Handles the scenario where a duplicate key is encountered during insertion.
     *
     * @param key The duplicate key encountered.
     */
    public static void handleDuplicateKey(int key) {
        System.out.println("Attempted to insertRecursive duplicate key: " + key);
    }
}
