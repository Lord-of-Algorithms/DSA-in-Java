package btree;

/**
 * Demonstrates {@link BTree}: search, insertion — including a split that adds
 * a level — and deletion with each of the repairs it can need. The trees are
 * the worked examples from the app's B-tree topic, all of maximum degree 4.
 */
public class BTreeMain {

    public static void main(String[] args) {
        demonstrateInsert();
        demonstrateDelete();
    }

    /**
     * Keys 10, 15, ..., 65 fill both the root and the leaf where 70
     * belongs. Inserting 70 overflows that leaf, which splits and sends 65
     * up; the root overflows in its turn and splits too, and the tree grows
     * a level.
     */
    private static void demonstrateInsert() {
        System.out.println("=== Insert ===");
        BTree tree = buildTree(10, 65, 5);
        System.out.println("After inserting 10, 15, ..., 65:");
        BTreePrinter.print(tree);
        System.out.println("contains(45) = " + tree.contains(45)
                + ", contains(47) = " + tree.contains(47));

        tree.insert(70);
        System.out.println("\nInsert 70 — the leaf splits, then the root:"
                + " one level more.");
        BTreePrinter.print(tree);
    }

    /**
     * Three deletions, one for each way a delete can end:
     * <ul>
     *   <li>a leaf left below the minimum borrows from its neighbour;</li>
     *   <li>two merges, one above the other, empty the root and the tree
     *       loses a level;</li>
     *   <li>a key in an internal node trades places with its successor and
     *       leaves from a leaf.</li>
     * </ul>
     */
    private static void demonstrateDelete() {
        System.out.println("\n=== Delete: borrowing from a neighbour ===");
        BTree tree = buildTree(10, 70, 10);
        BTreePrinter.print(tree);
        tree.delete(70);
        System.out.println("Delete 70 — its leaf empties and borrows through"
                + " the parent:");
        BTreePrinter.print(tree);

        System.out.println("\n=== Delete: a merge that removes a level ===");
        tree = buildTree(10, 70, 5);
        for (int key = 70; key >= 55; key -= 5) {
            tree.delete(key);
        }
        BTreePrinter.print(tree);
        tree.delete(40);
        System.out.println("Delete 40 — two merges empty the root:"
                + " one level less.");
        BTreePrinter.print(tree);

        System.out.println("\n=== Delete: a key in an internal node ===");
        tree = buildTree(10, 80, 10);
        BTreePrinter.print(tree);
        tree.delete(60);
        System.out.println("Delete 60 — it trades places with its successor"
                + " 70 and leaves from a leaf:");
        BTreePrinter.print(tree);
    }

    // A tree of maximum degree 4 holding first, first + step, ..., last,
    // inserted in ascending order.
    private static BTree buildTree(int first, int last, int step) {
        BTree tree = new BTree(4);
        for (int key = first; key <= last; key += step) {
            tree.insert(key);
        }
        return tree;
    }
}
