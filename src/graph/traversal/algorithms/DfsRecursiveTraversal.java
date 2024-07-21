package graph.traversal.algorithms;

import graph.Vertex;
import graph.traversal.graph.ExplorableGraph;

/**
 * Implements the recursive Depth-First Search (DFS) traversal algorithm for graph.
 */
public class DfsRecursiveTraversal extends BaseGraphTraversal {

    @Override
    public void traverse(ExplorableGraph graph, Vertex startVertex) {
        recursiveDfs(graph, startVertex);
    }

    /**
     * Recursively performs depth-first search starting from the given vertex entity.
     */
    private void recursiveDfs(ExplorableGraph graph, Vertex vertex) {
        visit(vertex);

        // Recursively visit all unvisited neighbor vertices
        Vertex unvisitedNeighbor;
        while ((unvisitedNeighbor = findUnvisitedNeighbor(graph, vertex)) != null) {
            recursiveDfs(graph, unvisitedNeighbor);
        }
    }

    @Override
    public String toString() {
        return "Recursive DFS";
    }
}
