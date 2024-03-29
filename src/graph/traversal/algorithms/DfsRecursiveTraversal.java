package graph.traversal.algorithms;

import graph.traversal.GraphTraversal;
import graph.traversal.UnweightedGraph;
import graph.traversal.Vertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implements the recursive Depth-First Search (DFS) traversal algorithm for graphs.
 */
public class DfsRecursiveTraversal implements GraphTraversal {

    private final List<Vertex> traversalPath;

    public DfsRecursiveTraversal() {
        traversalPath = new ArrayList<>();
    }

    @Override
    public void traverse(UnweightedGraph graph, Vertex vertex) {
        recursiveDfs(graph, vertex);
    }

    /**
     * Recursively performs depth-first search starting from the given vertex entity.
     */
    private void recursiveDfs(UnweightedGraph graph, Vertex vertex) {
        vertex.visit();
        traversalPath.add(vertex);

        // Recursively visit all unvisited adjacent vertices
        Vertex adjacent;
        while ((adjacent = graph.findUnvisitedAdjacent(vertex)) != null) {
            recursiveDfs(graph, adjacent);
        }
    }

    @Override
    public List<Vertex> getTraversalPath() {
        return Collections.unmodifiableList(traversalPath);
    }

    @Override
    public void resetState() {
        for (Vertex v : traversalPath) {
            v.resetVisitedStatus();
        }
        traversalPath.clear();
    }

    @Override
    public String toString() {
        return "Recursive DFS";
    }
}
