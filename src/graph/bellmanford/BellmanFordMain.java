package graph.bellmanford;

import graph.WeightedEdge;
import graph.Vertex;
import graph.VertexImpl;

import java.util.*;

/**
 * Demonstrates the Bellman-Ford algorithm for finding the shortest paths from a single source
 * vertex to all other vertices in a weighted directed graph.
 *
 * <p>Unlike Dijkstra's algorithm, Bellman-Ford correctly handles negative edge weights and can
 * detect negative weight cycles, which make shortest-path distances undefined.
 *
 * <p>Time complexity: O(V * E), where V is the number of vertices and E is the number of edges.
 */
public class BellmanFordMain {

    // Represents infinity: half of Integer.MAX_VALUE to avoid arithmetic overflow
    private static final int INFINITY = Integer.MAX_VALUE / 2;

    /**
     * Computes the shortest paths from a source vertex to all other vertices using the
     * Bellman-Ford algorithm.
     *
     * @param vertices list of all vertices in the graph
     * @param edges    list of all directed edges in the graph (may include negative weights)
     * @param source   the starting vertex
     * @return a {@link BellmanFordResult} containing distances, predecessors, and a negative-cycle flag
     * @throws IllegalArgumentException if the vertex or edge list is null or empty, or if the source is not in the graph
     */
    private static BellmanFordResult bellmanFord(List<Vertex> vertices, List<WeightedEdge> edges, Vertex source) {
        if (vertices == null || vertices.isEmpty()) {
            throw new IllegalArgumentException("Vertex list cannot be null or empty.");
        }
        if (edges == null || edges.isEmpty()) {
            throw new IllegalArgumentException("WeightedEdge list cannot be null or empty.");
        }
        if (!vertices.contains(source)) {
            throw new IllegalArgumentException("Source vertex is not in the vertex list.");
        }

        Map<Vertex, Integer> distances = new HashMap<>();
        Map<Vertex, Vertex> predecessors = new HashMap<>();

        // Step 1: Initialize distances — source is 0, everything else is infinity
        for (Vertex v : vertices) {
            distances.put(v, v.equals(source) ? 0 : INFINITY);
            predecessors.put(v, null);
        }

        int vertexCount = vertices.size();

        // Step 2: Relax all edges V-1 times
        boolean updated = false;
        for (int i = 0; i < vertexCount - 1; i++) {
            updated = false;

            for (WeightedEdge edge : edges) {
                Vertex u = edge.getSource();
                Vertex v = edge.getDestination();
                int distU = distances.get(u);

                if (distU != INFINITY) {
                    int newDist = distU + edge.getWeight();
                    if (newDist < distances.get(v)) {
                        distances.put(v, newDist);
                        predecessors.put(v, u);
                        updated = true;
                    }
                }
            }

            // Early termination: if no edge was relaxed in this pass, distances are final
            // and no negative cycle exists — Step 3 can be skipped entirely.
            if (!updated) {
                break;
            }
        }

        // Step 3: Detect negative weight cycles.
        // Only run if the loop completed all V-1 passes with updates still happening —
        // meaning distances never stabilised, which is the hallmark of a negative cycle.
        boolean hasNegativeCycle = false;
        if (updated) {
            for (WeightedEdge edge : edges) {
                Vertex u = edge.getSource();
                Vertex v = edge.getDestination();
                if (distances.get(u) != INFINITY && distances.get(u) + edge.getWeight() < distances.get(v)) {
                    hasNegativeCycle = true;
                    break;
                }
            }
        }

        return new BellmanFordResult(distances, predecessors, hasNegativeCycle);
    }

    /**
     * Holds the output of the Bellman-Ford algorithm: shortest distances from the source,
     * the predecessor map for path reconstruction, and a flag indicating a negative cycle.
     */
    private static class BellmanFordResult {
        final Map<Vertex, Integer> distances;
        final Map<Vertex, Vertex> predecessors;
        final boolean hasNegativeCycle;

        BellmanFordResult(Map<Vertex, Integer> distances,
                          Map<Vertex, Vertex> predecessors,
                          boolean hasNegativeCycle) {
            this.distances = distances;
            this.predecessors = predecessors;
            this.hasNegativeCycle = hasNegativeCycle;
        }
    }

    /**
     * Reconstructs the shortest path from the source to the given target vertex
     * by following the predecessor chain.
     *
     * @param predecessors the predecessor map produced by {@link #bellmanFord}
     * @param target       the destination vertex
     * @return a list of vertices from source to target, or an empty list if no path exists
     */
    private static List<Vertex> getPath(Map<Vertex, Vertex> predecessors, Vertex target) {
        List<Vertex> path = new ArrayList<>();
        path.add(target);
        Vertex predecessor = predecessors.get(target);
        while (predecessor != null) {
            path.add(predecessor);
            predecessor = predecessors.get(predecessor);
        }
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        demonstrateNormalGraph();
        System.out.println();
        demonstrateNegativeCycleGraph();
    }

    /**
     * Graph with a negative edge but no negative cycle.
     *
     * <pre>
     *   A --5--> B
     *   A --2--> D
     *   B --5--> C
     *   C --10-> F
     *   D --5--> E
     *   E --7--> F
     *   E --(-10)--> B
     * </pre>
     */
    private static void demonstrateNormalGraph() {
        System.out.println("=== Example 1: Negative edge, no negative cycle ===");

        Vertex a = new VertexImpl("A");
        Vertex b = new VertexImpl("B");
        Vertex c = new VertexImpl("C");
        Vertex d = new VertexImpl("D");
        Vertex e = new VertexImpl("E");
        Vertex f = new VertexImpl("F");

        List<Vertex> vertices = new ArrayList<>(Arrays.asList(a, b, c, d, e, f));

        List<WeightedEdge> edges = new ArrayList<>();
        edges.add(new WeightedEdge(a, b, 5));
        edges.add(new WeightedEdge(a, d, 2));
        edges.add(new WeightedEdge(b, c, 5));
        edges.add(new WeightedEdge(c, f, 10));
        edges.add(new WeightedEdge(d, e, 5));
        edges.add(new WeightedEdge(e, f, 7));
        edges.add(new WeightedEdge(e, b, -10));

        BellmanFordResult result = bellmanFord(vertices, edges, a);

        if (result.hasNegativeCycle) {
            System.out.println("Negative cycle detected — distances are unreliable.");
        } else {
            System.out.println("Shortest distances and paths from source A:");
            for (Vertex v : vertices) {
                int dist = result.distances.get(v);
                List<Vertex> path = getPath(result.predecessors, v);
                System.out.println("  " + v + ": distance=" + dist + ", path=" + path);
            }
        }
    }

    /**
     * Graph containing a negative weight cycle (B → C → F → E → B, total weight = −6).
     *
     * <pre>
     *   A --5--> B
     *   A --2--> D
     *   B --3--> C
     *   C --4--> F
     *   D --5--> E
     *   F --2--> E
     *   E --(-15)--> B  ← closes the negative cycle
     * </pre>
     */
    private static void demonstrateNegativeCycleGraph() {
        System.out.println("=== Example 2: Graph with a negative cycle ===");

        Vertex a = new VertexImpl("A");
        Vertex b = new VertexImpl("B");
        Vertex c = new VertexImpl("C");
        Vertex d = new VertexImpl("D");
        Vertex e = new VertexImpl("E");
        Vertex f = new VertexImpl("F");

        List<Vertex> vertices = new ArrayList<>(Arrays.asList(a, b, c, d, e, f));

        List<WeightedEdge> edges = new ArrayList<>();
        edges.add(new WeightedEdge(a, b, 5));
        edges.add(new WeightedEdge(a, d, 2));
        edges.add(new WeightedEdge(b, c, 3));
        edges.add(new WeightedEdge(c, f, 4));
        edges.add(new WeightedEdge(d, e, 5));
        edges.add(new WeightedEdge(f, e, 2));
        edges.add(new WeightedEdge(e, b, -15));  // Creates negative cycle: B → C → F → E → B

        BellmanFordResult result = bellmanFord(vertices, edges, a);

        if (result.hasNegativeCycle) {
            System.out.println("Negative cycle detected — no finite shortest paths exist.");
        } else {
            System.out.println("No negative cycle found.");
        }
    }
}
