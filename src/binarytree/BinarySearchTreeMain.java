package binarytree;

import binarytree.bst.ITree;
import binarytree.bst.avl.AvlTree;
import binarytree.bst.red_black.RedBlackTree;
import binarytree.bst.standard.BinarySearchTreeIterative;
import binarytree.bst.standard.BinarySearchTreeRecursive;
import binarytree.traversal.*;

public class BinarySearchTreeMain {
    /**
     * Main method to demonstrate the operations of a binary search tree.
     */
    public static void main(String[] args) {
        // Create a binary search tree with the specified type:
        // BST (StandardIterative or StandardRecursive), AVL and Red-Black.
        ITree tree = createTree(BstType.RedBlack);

        // Inserting various keys into the tree.
        insertKeysIntoTree(tree, new int[]{20, 7, 9, 2, 40, 22, 70, 70, 25});

        // Display the tree after insertions.
        System.out.println("Binary tree after insertions:");
        BinaryTreePrinter.print(tree.getRoot());

        // Deleting specific nodes from the tree and displaying the tree after each deletion.
        deleteKeysFromTree(tree, new int[]{20, 40, 2, 7});

        // Search for nodes in the tree and display the results.
        searchKeysInTree(tree, new int[]{70, 44});

        // Demonstrating various traversal strategies on the tree.
        demonstrateTraversals(tree);
    }

    /**
     * Enumeration to specify the type of a binary search tree.
     */
    private enum BstType {
        StandardIterative, // Standard binary search tree with iterative nature of insert, delete and search operations
        StandardRecursive, // Standard binary search tree with recursive nature of insert, delete and search operations
        Avl, // Self-balanced Avl tree
        RedBlack // Self-balanced Red-black tree
    }

    /**
     * Factory method to create a binary search tree of the specified operational nature.
     *
     * @param treeType The type of the binary search tree.
     * @return An instance of ITree corresponding to the specified type.
     */
    private static ITree createTree(BstType treeType) {
        switch (treeType) {
            case StandardIterative:
                return new BinarySearchTreeIterative();
            case StandardRecursive:
                return new BinarySearchTreeRecursive();
            case Avl:
                return new AvlTree();
            case RedBlack:
                return new RedBlackTree();
            default:
                throw new IllegalArgumentException("Unknown bst type: " + treeType);
        }
    }

    /**
     * Performs insertions of a set of keys into the binary search tree.
     *
     * @param tree The binary search tree instance.
     * @param keys An array of keys to delete.
     */
    private static void insertKeysIntoTree(ITree tree, int[] keys) {
        for (int key : keys) {
            System.out.println("Inserting key: " + key);
            tree.insert(key);
        }
    }

    /**
     * Deletes a series of keys from the binary search tree.
     *
     * @param tree The binary search tree instance.
     * @param keys An array of keys to delete.
     */
    private static void deleteKeysFromTree(ITree tree, int[] keys) {
        for (int key : keys) {
            System.out.println("Delete node with key: " + key);
            tree.delete(key);
            BinaryTreePrinter.print(tree.getRoot());
        }
    }

    /**
     * Searches for a series of keys in the binary search tree and prints whether each key is found.
     *
     * @param tree The binary search tree instance.
     * @param keys An array of keys to search for.
     */
    private static void searchKeysInTree(ITree tree, int[] keys) {
        for (int key : keys) {
            System.out.println("Search for a node with key: " + key);
            System.out.println("Is found: " + (tree.search(key) != null));
        }
    }

    /**
     * Demonstrates various traversal methods on the binary search tree.
     *
     * @param tree The binary search tree instance.
     */
    private static void demonstrateTraversals(ITree tree) {
        // Iterative pre-order traversal
        ITraversal traversal = new PreOrderTraversal(TraversalNature.Iterative);
        System.out.println("\nPre-order traversal: ");
        traversal.traverse(tree.getRoot());

        // Recursive in-order traversal
        System.out.println("\nIn-order traversal: ");
        traversal = new InOrderTraversal(TraversalNature.Recursive);
        traversal.traverse(tree.getRoot());

        // Recursive post-order traversal
        System.out.println("\nPost-order traversal: ");
        traversal = new PostOrderTraversal(TraversalNature.Recursive);
        traversal.traverse(tree.getRoot());

        // Breadth-first traversal
        System.out.println("\nBreadth-first traversal: ");
        traversal = new BreadthFirstTraversal();
        traversal.traverse(tree.getRoot());
    }
}