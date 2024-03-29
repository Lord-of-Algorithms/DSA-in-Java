package graph.traversal.main;

import graph.traversal.GraphTraversal;
import graph.traversal.UnweightedGraph;

public class TraversalMain {

    /**
     * Demonstrates graph creation, edge setup, and traversal using both Depth-First Search (DFS)
     * and Breadth-First Search (BFS) algorithms.
     */
    public static void main(String[] args) {
        // Create vertices for the graph
        GraphVertex a = new GraphVertex("A");
        GraphVertex b = new GraphVertex("B");
        GraphVertex c = new GraphVertex("C");
        GraphVertex d = new GraphVertex("D");

        // Create an unweighted graph using the factory method and add vertices to it
        UnweightedGraph graph = GraphFactory.createGraph(GraphRepresentation.MATRIX, new GraphVertex[]{a, b, c, d});

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
