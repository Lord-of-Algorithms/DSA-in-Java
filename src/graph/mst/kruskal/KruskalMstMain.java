package graph.mst.kruskal;

import graph.Edge;
import graph.Vertex;
import graph.VertexImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Demonstrates the use of Kruskal's algorithm to find the minimum spanning tree (MST) of a graph.
 * This class highlights how Kruskal's algorithm is particularly suited for graphs represented as a list of edges,
 * as it processes edges by increasing weight, irrespective of their position in the graph. This edge list representation
 * is used because Kruskal's algorithm does not require direct access to the graph's adjacency structure, making it
 * efficient and straightforward for calculating the MST in graphs where edge connectivity is the primary concern.
 * <p>
 * The main method sets up vertices and edges, then uses Kruskal's algorithm to calculate and display the MST.
 */
public class KruskalMstMain {
    public static void main(String[] args) {

        List<Vertex> vertices = new ArrayList<>();
        // Create vertices
        Vertex a = new VertexImpl("A");
        Vertex b = new VertexImpl("B");
        Vertex c = new VertexImpl("C");
        Vertex d = new VertexImpl("D");

        vertices.add(a);
        vertices.add(b);
        vertices.add(c);
        vertices.add(d);

        List<Edge> edges = new ArrayList<>();
        // Set edges between vertices with specified weights
        edges.add(new Edge(a, b, 1));
        edges.add(new Edge(d, b, 2));
        edges.add(new Edge(b, c, 3));
        edges.add(new Edge(a, d, 4));
        edges.add(new Edge(d, c, 5));

        // Calculate the MST using Kruskal's algorithm
        List<Edge> mst = buildMst(vertices, edges);
        System.out.println("MST: " + mst);
    }

    /**
     * Constructs the minimum spanning tree (MST) for a graph represented by vertices and edges.
     * Assumes that the graph is connected.
     */
    private static List<Edge> buildMst(List<Vertex> vertices, List<Edge> edges) {
        if (vertices == null || vertices.isEmpty()) {
            throw new IllegalArgumentException("Vertex list cannot be null or empty.");
        }
        if (edges == null || edges.isEmpty()) {
            throw new IllegalArgumentException("Edge list cannot be null or empty.");
        }

        List<Edge> mst = new ArrayList<>();

        // Sorting edges by weight
        edges.sort(new Comparator<Edge>() {
            @Override
            public int compare(Edge edge1, Edge edge2) {
                return edge1.getWeight() - edge2.getWeight();
            }
        });
        UnionFind uf = new UnionFind(vertices);

        for (Edge edge : edges) {
            if (uf.find(edge.getSource()) != uf.find(edge.getDestination())) {
                mst.add(edge);
                uf.union(edge.getSource(), edge.getDestination());
            }
        }
        return mst;
    }
}
