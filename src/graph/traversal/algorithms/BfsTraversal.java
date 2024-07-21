package graph.traversal.algorithms;

import graph.Vertex;
import graph.traversal.graph.ExplorableGraph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Implements the Breadth-First Search (BFS) traversal algorithm for graph.
 */
public class BfsTraversal extends BaseGraphTraversal {

    @Override
    public void traverse(ExplorableGraph graph, Vertex startVertex) {
        Queue<Vertex> queue = new LinkedList<>();
        visit(startVertex);
        queue.add(startVertex);
        while (!queue.isEmpty()) {
            Vertex head = queue.remove();
            Vertex unvisitedNeighbor;
            while ((unvisitedNeighbor = findUnvisitedNeighbor(graph, head)) != null) {
                visit(unvisitedNeighbor);
                queue.add(unvisitedNeighbor);
            }
        }
    }

    @Override
    public String toString() {
        return "BFS";
    }
}
