package graph.mst.kruskal;

import graph.Vertex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implements the union-find data structure.
 */
class UnionFind {
    private final Map<Vertex, Vertex> parent;
    private final Map<Vertex, Integer> rank;

    /**
     * Constructs a union-find structure with a list of initial vertices.
     * Each vertex is initially its own set with a rank of 0.
     */
    UnionFind(List<Vertex> vertices) {
        parent = new HashMap<>();
        rank = new HashMap<>();
        for (Vertex vertex : vertices) {
            parent.put(vertex, vertex);
            rank.put(vertex, 0);
        }
    }

    /**
     * Finds the representative of the set containing the specified vertex.
     * Implements path compression.
     */
    Vertex find(Vertex vertex) {
        if (!vertex.equals(parent.get(vertex))) {
            parent.put(vertex, find(parent.get(vertex)));
        }
        return parent.get(vertex);
    }

    /**
     * Unites the two sets that contain vertex 'x' and 'y'.
     */
    void union(Vertex x, Vertex y) {
        Vertex rootX = find(x);
        Vertex rootY = find(y);
        if (!rootX.equals(rootY)) {
            if (rank.get(rootX) < rank.get(rootY)) {
                parent.put(rootX, rootY);
            } else if (rank.get(rootX) > rank.get(rootY)) {
                parent.put(rootY, rootX);
            } else {
                parent.put(rootY, rootX);
                rank.put(rootX, rank.get(rootX) + 1);
            }
        }
    }
}
