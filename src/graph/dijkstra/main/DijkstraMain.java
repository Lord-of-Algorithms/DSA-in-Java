package graph.dijkstra.main;

import graph.GraphRepresentation;
import graph.Vertex;
import graph.VertexImpl;
import graph.dijkstra.DijkstraAlgorithm;
import graph.dijkstra.graph.DijkstraGraph;

import java.util.List;

/**
 * This class demonstrates the use of Dijkstra's algorithm to find shortest paths in a graph.
 */
public class DijkstraMain {
    public static void main(String[] args) {
        // Create vertices for the graph
        Vertex a = new VertexImpl("A");
        Vertex b = new VertexImpl("B");
        Vertex c = new VertexImpl("C");
        Vertex d = new VertexImpl("D");

        DijkstraGraph graph = GraphFactory.createGraph(GraphRepresentation.LIST, new Vertex[]{a, b, c, d});

        graph.setEdge(a, b, 13);
        graph.setEdge(c, b, 18);
        graph.setEdge(a, c, 13);
        graph.setEdge(b, d, 28);
        graph.setEdge(c, d, 85);

        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm();
        dijkstraAlgorithm.computePaths(graph, a);
        List<Vertex> path = dijkstraAlgorithm.getShortestPathTo(d);
        System.out.println("Initial path to D: " + path);

        // Update edges and recompute paths
        dijkstraAlgorithm.resetState();
        graph.setEdge(a, d, 10); // Direct path added
        dijkstraAlgorithm.computePaths(graph, a);
        path = dijkstraAlgorithm.getShortestPathTo(d);
        System.out.println("Updated path to D with direct A to D: " + path);

        dijkstraAlgorithm.resetState();
        graph.setEdge(a, b, 53); // Increased weight A to B
        graph.setEdge(a, d, 100); // Less optimal direct path A to D
        dijkstraAlgorithm.computePaths(graph, a);
        path = dijkstraAlgorithm.getShortestPathTo(d);
        System.out.println("Path after changing weights: " + path);
    }
}
