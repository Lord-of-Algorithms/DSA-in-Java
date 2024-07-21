package graph.traversal.algorithms;

import graph.Vertex;
import graph.traversal.graph.ExplorableGraph;

import java.util.*;

/**
 * Base class for graph traversal algorithms. This class provides common functionalities
 * needed for traversing a graph, such as marking vertices as visited and tracking the
 * traversal path.
 */
abstract class BaseGraphTraversal implements GraphTraversal {

    private final Set<Vertex> visitedStatusSet;
    private final List<Vertex> traversalPath;

    BaseGraphTraversal() {
        visitedStatusSet = new HashSet<>();
        traversalPath = new ArrayList<>();
    }

    /**
     * Finds an unvisited neighbor of the specified vertex in the given graph.
     *
     * @param vertex The vertex whose unvisited neighbor is to be found.
     * @return The first unvisited neighbor of the vertex, or null if all neighbors have been visited.
     */
    Vertex findUnvisitedNeighbor(ExplorableGraph graph, Vertex vertex) {
        List<Vertex> neighbors = graph.getNeighbors(vertex);
        for (Vertex v : neighbors) {
            if (!isVisited(v)) {
                return v;
            }
        }
        return null;
    }

    private boolean isVisited(Vertex vertex) {
        return visitedStatusSet.contains(vertex);
    }

    protected void visit(Vertex vertex) {
        visitedStatusSet.add(vertex);
        traversalPath.add(vertex);
    }

    @Override
    public List<Vertex> getTraversalPath() {
        return Collections.unmodifiableList(traversalPath);
    }

    @Override
    public void resetState() {
        visitedStatusSet.clear();
        traversalPath.clear();
    }
}
