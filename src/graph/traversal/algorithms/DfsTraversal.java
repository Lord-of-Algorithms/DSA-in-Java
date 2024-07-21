package graph.traversal.algorithms;

import graph.Vertex;
import graph.traversal.graph.ExplorableGraph;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Implements the Depth-First Search (DFS) traversal algorithm for graph.
 */
public class DfsTraversal extends BaseGraphTraversal {

    @Override
    public void traverse(ExplorableGraph graph, Vertex startVertex) {
        Deque<Vertex> stack = new ArrayDeque<>();
        visit(startVertex);
        stack.push(startVertex);
        while (!stack.isEmpty()) {
            Vertex unvisitedNeighbor = findUnvisitedNeighbor(graph, stack.peek());
            if (unvisitedNeighbor == null) {
                stack.pop();
            } else {
                visit(unvisitedNeighbor);
                stack.push(unvisitedNeighbor);
            }
        }
    }

    @Override
    public String toString() {
        return "DFS";
    }
}
