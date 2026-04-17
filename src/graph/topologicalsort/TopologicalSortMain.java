package graph.topologicalsort;

import graph.Edge;
import graph.Vertex;
import graph.VertexImpl;

import java.util.*;

/**
 * Demonstrates Kahn's algorithm for computing a topological ordering of a
 * directed acyclic graph (DAG).
 *
 * <p>A topological ordering is a linear sequence of all vertices such that for
 * every directed edge u → v, vertex u appears before vertex v in the sequence.
 *
 * <p>Kahn's algorithm uses in-degrees — the number of incoming edges per vertex —
 * to determine which vertices are ready to be processed at each step.
 *
 * <p>Time complexity: O(V + E), where V is the number of vertices and E is the number of edges.
 */
public class TopologicalSortMain {

    /**
     * Computes a topological ordering of the given DAG using Kahn's algorithm.
     *
     * <p>Edge weights are not used — only the direction of each edge matters.
     *
     * @param vertices list of all vertices in the graph
     * @param edges    list of all directed edges in the graph
     * @return a {@link TopologicalSortResult} containing the ordered vertex list and a cycle flag
     * @throws IllegalArgumentException if the vertex list is null or empty, or if the edge list is null
     */
    private static TopologicalSortResult topologicalSort(List<Vertex> vertices, List<Edge> edges) {
        if (vertices == null || vertices.isEmpty()) {
            throw new IllegalArgumentException("Vertex list cannot be null or empty.");
        }
        if (edges == null) {
            throw new IllegalArgumentException("Edge list cannot be null.");
        }

        // Step 1: Compute in-degrees and build adjacency list from the edge list
        Map<Vertex, Integer> inDegree = new HashMap<>();
        Map<Vertex, List<Vertex>> adjacency = new HashMap<>();
        for (Vertex v : vertices) {
            inDegree.put(v, 0);
            adjacency.put(v, new ArrayList<>());
        }
        for (Edge edge : edges) {
            Vertex source = edge.getSource();
            Vertex destination = edge.getDestination();

            adjacency.get(source).add(destination);
            inDegree.put(destination, inDegree.get(destination) + 1);
        }

        // Step 2: Enqueue all vertices with in-degree 0 — they have no dependencies
        Queue<Vertex> queue = new LinkedList<>();
        for (Vertex v : vertices) {
            if (inDegree.get(v) == 0) {
                queue.add(v);
            }
        }

        // Step 3: Process the queue
        List<Vertex> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            Vertex vertex = queue.poll();
            result.add(vertex);

            for (Vertex neighbor : adjacency.get(vertex)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        // Cycle detection: if not all vertices were processed, a cycle exists
        boolean hasCycle = result.size() != vertices.size();
        return new TopologicalSortResult(result, hasCycle);
    }

    /**
     * Holds the output of Kahn's algorithm: the topological order and a flag
     * indicating whether a cycle was detected.
     */
    private static class TopologicalSortResult {
        final List<Vertex> order;
        final boolean hasCycle;

        TopologicalSortResult(List<Vertex> order, boolean hasCycle) {
            this.order = order;
            this.hasCycle = hasCycle;
        }
    }

    public static void main(String[] args) {
        demonstrateDAG();
        System.out.println();
        demonstrateCyclicGraph();
    }

    /**
     * A directed acyclic graph representing a simple dependency structure.
     *
     * <pre>
     *   A --> B
     *   A --> C
     *   B --> D
     *   C --> D
     *   D --> E
     * </pre>
     */
    private static void demonstrateDAG() {
        System.out.println("=== Example 1: Directed acyclic graph ===");

        Vertex a = new VertexImpl("A");
        Vertex b = new VertexImpl("B");
        Vertex c = new VertexImpl("C");
        Vertex d = new VertexImpl("D");
        Vertex e = new VertexImpl("E");

        List<Vertex> vertices = new ArrayList<>(Arrays.asList(a, b, c, d, e));
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(a, b));
        edges.add(new Edge(a, c));
        edges.add(new Edge(b, d));
        edges.add(new Edge(c, d));
        edges.add(new Edge(d, e));

        printResult(topologicalSort(vertices, edges));
    }

    /**
     * A graph containing a cycle (B → C → D → B), making topological sort impossible.
     *
     * <pre>
     *   A --> B
     *   B --> C
     *   C --> D
     *   D --> B  ← closes the cycle
     * </pre>
     */
    private static void demonstrateCyclicGraph() {
        System.out.println("=== Example 2: Graph with a cycle ===");

        Vertex a = new VertexImpl("A");
        Vertex b = new VertexImpl("B");
        Vertex c = new VertexImpl("C");
        Vertex d = new VertexImpl("D");

        List<Vertex> vertices = new ArrayList<>(Arrays.asList(a, b, c, d));
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(a, b));
        edges.add(new Edge(b, c));
        edges.add(new Edge(c, d));
        edges.add(new Edge(d, b));  // Creates cycle: B → C → D → B

        printResult(topologicalSort(vertices, edges));
    }

    private static void printResult(TopologicalSortResult result) {
        if (result.hasCycle) {
            System.out.println("Cycle detected — topological sort is not possible.");
        } else {
            System.out.println("Topological order: " + result.order);
        }
    }
}
