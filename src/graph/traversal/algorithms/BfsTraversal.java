package graph.traversal.algorithms;

import graph.traversal.GraphTraversal;
import graph.traversal.UnweightedGraph;
import graph.traversal.Vertex;

import java.util.*;

/**
 * Implements the Breadth-First Search (BFS) traversal algorithm for graphs.
 */
public class BfsTraversal implements GraphTraversal {

    private final List<Vertex> traversalPath;

    public BfsTraversal() {
        traversalPath = new ArrayList<>();
    }

    @Override
    public void traverse(UnweightedGraph graph, Vertex vertex) {
        Queue<Vertex> queue = new LinkedList<>();
        vertex.visit();
        traversalPath.add(vertex);
        queue.add(vertex);
        while (!queue.isEmpty()) {
            Vertex head = queue.remove();
            Vertex adjacent;
            while ((adjacent = graph.findUnvisitedAdjacent(head)) != null) {
                adjacent.visit();
                traversalPath.add(adjacent);
                queue.add(adjacent);
            }
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
        return "BFS";
    }
}
