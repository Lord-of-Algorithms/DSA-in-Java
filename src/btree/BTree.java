package btree;

import java.util.ArrayList;
import java.util.List;

/**
 * A <b>B-tree</b> of distinct integer keys.
 *
 * <p>A B-tree is a balanced search tree whose nodes hold several keys at once.
 * The keys inside a node are kept in ascending order, and a node holding
 * {@code k} keys, unless it is a leaf, has {@code k + 1} children: one for
 * each gap between two keys, and one at either end. Every key in a child lies
 * in the range its gap describes.
 *
 * <p>The size of the nodes is set by the <b>maximum degree</b> {@code m}: the
 * most children a node may have. From it follow the two rules every node
 * obeys:
 * <ul>
 *   <li>at most {@code m - 1} keys — one more and the node splits;</li>
 *   <li>at least {@code ceil(m / 2) - 1} keys, for every node but the root —
 *       one fewer and the node borrows a key from a neighbour or merges with
 *       one.</li>
 * </ul>
 *
 * <p>Every leaf sits at the same depth. A new key joins a leaf that already
 * exists, and the tree grows taller only when its root splits, which adds a
 * level above the whole tree at once. Deleting runs the same machinery
 * backwards, and the tree grows shorter only when a merge empties the root.
 *
 * <p>{@link #contains}, {@link #insert} and {@link #delete} each walk one path
 * from the root to a leaf and back, so all three are {@code O(log n)}.
 */
public class BTree {

    // A node of the tree. Package-private, so that BTreePrinter can walk it.
    static class Node {
        final List<Integer> keys = new ArrayList<>();
        final List<Node> children = new ArrayList<>();

        boolean isLeaf() {
            return children.isEmpty();
        }
    }

    private final int maxKeys;
    private final int minKeys;
    private Node root = new Node();

    /**
     * Creates an empty B-tree whose nodes may have up to {@code maxDegree}
     * children.
     *
     * @throws IllegalArgumentException if {@code maxDegree} is less than 3
     */
    public BTree(int maxDegree) {
        if (maxDegree < 3) {
            throw new IllegalArgumentException("Max degree must be at least 3");
        }
        maxKeys = maxDegree - 1;
        minKeys = (maxDegree + 1) / 2 - 1; // ceil(maxDegree / 2) - 1
    }

    /**
     * Returns {@code true} if the tree holds no keys.
     */
    public boolean isEmpty() {
        return root.keys.isEmpty();
    }

    // The node the tree hangs from, for BTreePrinter.
    Node root() {
        return root;
    }

    // Index of the first key in the node that is not smaller than key — the
    // key itself if the node holds it, otherwise the gap it falls into.
    private static int lowerBound(Node node, int key) {
        int i = 0;
        while (i < node.keys.size() && node.keys.get(i) < key) {
            i++;
        }
        return i;
    }

    /**
     * Returns {@code true} if the tree holds {@code key}.
     */
    public boolean contains(int key) {
        Node node = root;
        while (true) {
            int i = lowerBound(node, key);
            if (i < node.keys.size() && node.keys.get(i) == key) {
                return true;
            }
            if (node.isLeaf()) {
                return false;
            }
            // Descend through the gap the key falls into.
            node = node.children.get(i);
        }
    }

    /**
     * Inserts {@code key}. The key goes into the leaf it belongs in; if that
     * leaf now holds one key too many it splits, and the split can repeat all
     * the way up to the root.
     *
     * @return {@code false} if the key was already in the tree
     */
    public boolean insert(int key) {
        // Step 1: walk down to the leaf the key belongs in, remembering the
        // path so the splits can climb back up it.
        List<Node> path = new ArrayList<>();
        Node node = root;
        while (true) {
            int i = lowerBound(node, key);
            if (i < node.keys.size() && node.keys.get(i) == key) {
                return false; // already in the tree
            }
            if (node.isLeaf()) {
                // Step 2: the leaf makes room and takes the key.
                node.keys.add(i, key);
                break;
            }
            path.add(node);
            node = node.children.get(i);
        }

        // Step 3: while a node holds one key too many, split it. Its middle
        // key goes up into the parent, which may overflow in its turn.
        while (node.keys.size() > maxKeys) {
            Node parent = path.isEmpty() ? null : path.remove(path.size() - 1);
            split(node, parent);
            if (parent == null) {
                break; // the root split: the tree is one level taller
            }
            node = parent;
        }
        return true;
    }

    // Splits an overfull node in two around its middle key, which moves up
    // into the parent. With an even number of keys there are two middles;
    // the right-hand one goes up, leaving the extra key in the left half.
    // A split root grows a new root above it.
    private void split(Node node, Node parent) {
        int mid = node.keys.size() / 2;
        int middleKey = node.keys.remove(mid);

        Node right = new Node();
        while (node.keys.size() > mid) {
            right.keys.add(node.keys.remove(mid));
        }
        if (!node.isLeaf()) {
            while (node.children.size() > mid + 1) {
                right.children.add(node.children.remove(mid + 1));
            }
        }

        if (parent == null) {
            parent = new Node();
            parent.children.add(node);
            root = parent;
        }
        int at = parent.children.indexOf(node);
        parent.keys.add(at, middleKey);
        parent.children.add(at + 1, right);
    }

    /**
     * Deletes {@code key}. A key can only leave a leaf, so a key inside an
     * internal node first trades places with its successor. Removing it may
     * leave a node below the minimum, which is repaired from the bottom up by
     * borrowing a key from a neighbour or merging with one.
     *
     * @return {@code false} if the key was not in the tree
     */
    public boolean delete(int key) {
        // Step 1: walk down to the key, remembering the path so the repair
        // can climb back up it.
        List<Node> path = new ArrayList<>();
        Node node = root;
        int index;
        while (true) {
            index = lowerBound(node, key);
            if (index < node.keys.size() && node.keys.get(index) == key) {
                break;
            }
            if (node.isLeaf()) {
                return false; // not in the tree
            }
            path.add(node);
            node = node.children.get(index);
        }

        // Step 2: a key inside an internal node trades places with its
        // successor — the leftmost key of the subtree to its right — so
        // that it can leave from a leaf.
        if (!node.isLeaf()) {
            Node holder = node;
            path.add(node);
            node = node.children.get(index + 1);
            while (!node.isLeaf()) {
                path.add(node);
                node = node.children.get(0);
            }
            holder.keys.set(index, node.keys.get(0));
            node.keys.set(0, key);
            index = 0;
        }

        // Step 3: the leaf gives the key up. It may now be below the
        // minimum — even empty — and stays that way until Step 4 repairs it.
        node.keys.remove(index);

        // Step 4: repair from the bottom up. A node below the minimum borrows
        // a spare key from a neighbour, through the parent; if neither
        // neighbour has one, it merges with a neighbour and the key that
        // separated them, which takes a key from the parent — so the repair
        // continues one level higher.
        while (node != root && node.keys.size() < minKeys) {
            Node parent = path.remove(path.size() - 1);
            int at = parent.children.indexOf(node);
            Node left = at > 0 ? parent.children.get(at - 1) : null;
            Node right = at < parent.children.size() - 1
                    ? parent.children.get(at + 1) : null;

            if (left != null && left.keys.size() > minKeys) {
                borrowFromLeft(parent, at);
            } else if (right != null && right.keys.size() > minKeys) {
                borrowFromRight(parent, at);
            } else {
                // Merge with the left neighbour if there is one.
                int first = at > 0 ? at - 1 : at;
                merge(parent, first);
                if (parent == root && parent.keys.isEmpty()) {
                    // The tree is one level shorter.
                    root = parent.children.get(0);
                    break;
                }
            }
            node = parent;
        }
        return true;
    }

    // The separating key comes down from the parent to the front of the child
    // at index at; the left neighbour's last key goes up to replace it.
    private void borrowFromLeft(Node parent, int at) {
        Node node = parent.children.get(at);
        Node left = parent.children.get(at - 1);
        node.keys.add(0, parent.keys.get(at - 1));
        parent.keys.set(at - 1, left.keys.remove(left.keys.size() - 1));
        if (!left.isLeaf()) {
            int last = left.children.size() - 1;
            node.children.add(0, left.children.remove(last));
        }
    }

    // The separating key comes down from the parent to the end of the child at
    // index at; the right neighbour's first key goes up to replace it.
    private void borrowFromRight(Node parent, int at) {
        Node node = parent.children.get(at);
        Node right = parent.children.get(at + 1);
        node.keys.add(parent.keys.get(at));
        parent.keys.set(at, right.keys.remove(0));
        if (!right.isLeaf()) {
            node.children.add(right.children.remove(0));
        }
    }

    // Merges the child at index first + 1 into the child at index first, with
    // the key that separated them in between.
    private void merge(Node parent, int first) {
        Node left = parent.children.get(first);
        Node right = parent.children.get(first + 1);
        left.keys.add(parent.keys.remove(first));
        left.keys.addAll(right.keys);
        left.children.addAll(right.children);
        parent.children.remove(first + 1);
    }
}
