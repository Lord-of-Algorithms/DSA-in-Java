package graph.traversal.main;

import graph.GraphRepresentation;
import graph.Vertex;
import graph.VertexImpl;
import graph.traversal.algorithms.GraphTraversal;
import graph.traversal.graph.ExplorableGraph;

public class TraversalMain {

    /**
     * Demonstrates graph creation, edge setup, and traversal using both Depth-First Search (DFS)
     * and Breadth-First Search (BFS) algorithms.
     */
    public static void main(String[] args) {
        // Create vertices for the graph
        Vertex a = new VertexImpl("A");
        Vertex b = new VertexImpl("B");
        Vertex c = new VertexImpl("C");
        Vertex d = new VertexImpl("D");

        // Create an unweighted graph using the factory method and add vertices to it
        ExplorableGraph graph = GraphFactory.createGraph(GraphRepresentation.LIST, new Vertex[]{a, b, c, d});

        // Establish edges between the vertices
        graph.setEdge(a, b);
        graph.setEdge(a, d);
        graph.setEdge(b, c);
        graph.setEdge(b, d);
        graph.setEdge(c, d);

        // Create a Depth-First Search traversal instance
        GraphTraversal traversal = GraphTraversalFactory.createTraversal(TraversalMethod.DFS);

        // Perform traversal starting from vertex 'a'
        traversal.traverse(graph, a);
        System.out.println(traversal + ": " + traversal.getTraversalPath());
        // Reset the traversal state
        traversal.resetState();

        // Create a Breadth-First Search traversal instance
        traversal = GraphTraversalFactory.createTraversal(TraversalMethod.BFS);
        traversal.traverse(graph, a);
        System.out.println(traversal + ": " + traversal.getTraversalPath());
        // Note: There's no resetState call here as traversal is not reused afterward
    }
}
