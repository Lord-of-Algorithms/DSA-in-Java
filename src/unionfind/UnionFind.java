package unionfind;

/**
 * Implements the union-find data structure (also known as disjoint-set).
 * This implementation uses path compression in the find operation.
 */
public class UnionFind {

    private static class Set {
        // the parent index of the element
        int parent;
        int rank;

        Set(int parent, int rank) {
            this.parent = parent;
            this.rank = rank;
        }
    }

    private final Set[] sets;

    /**
     * Initializes the UnionFind structure with a specified number of elements.
     * Each element is initially in its own set.
     */
    public UnionFind(int count) {
        sets = new Set[count];
        for (int i = 0; i < count; i++) {
            sets[i] = new Set(i, 0);
        }
    }

    /**
     * Finds the representative (root) of the set containing 'element'
     * and applies path compression.
     *
     * @return the representative of the set containing 'element'
     */
    public int find(int element) {
        if (element < 0 || element >= sets.length) {
            throw new IllegalArgumentException("Element out of bounds");
        }
        if (sets[element].parent != element)
            sets[element].parent = find(sets[element].parent);
        return sets[element].parent;
    }

    /**
     * Merges the sets containing elements 'x' and 'y'.
     *
     * @param x an element of the first set
     * @param y an element of the second set
     */
    public void union(int x, int y) {
        if (x < 0 || x >= sets.length || y < 0 || y >= sets.length) {
            throw new IllegalArgumentException("Element out of bounds");
        }

        int xRoot = find(x);
        int yRoot = find(y);

        if (xRoot != yRoot) {
            if (sets[xRoot].rank < sets[yRoot].rank) {
                sets[xRoot].parent = yRoot;
            } else if (sets[xRoot].rank > sets[yRoot].rank) {
                sets[yRoot].parent = xRoot;
            } else {
                sets[yRoot].parent = xRoot;
                sets[xRoot].rank++;
            }
        }
    }
}
