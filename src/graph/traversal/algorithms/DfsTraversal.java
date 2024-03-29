package graph.traversal.algorithms;

import graph.traversal.GraphTraversal;
import graph.traversal.UnweightedGraph;
import graph.traversal.Vertex;

import java.util.*;

/**
 * Implements the Depth-First Search (DFS) traversal algorithm for graphs.
 */
public class DfsTraversal implements GraphTraversal {

    private final List<Vertex> traversalPath;

    public DfsTraversal() {
        traversalPath = new ArrayList<>();
    }

    @Override
    public void traverse(UnweightedGraph graph, Vertex vertex) {
        Deque<Vertex> stack = new ArrayDeque<>();
        vertex.visit();
        traversalPath.add(vertex);
        stack.push(vertex);
        while (!stack.isEmpty()) {
            Vertex adjacent = graph.findUnvisitedAdjacent(stack.peek());
            if (adjacent == null) {
                stack.pop();
            } else {
                adjacent.visit();
                traversalPath.add(adjacent);
                stack.push(adjacent);
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
        return "DFS";
    }
}
