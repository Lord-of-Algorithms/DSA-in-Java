package graph.traversal.algorithms;

import graph.Vertex;
import graph.traversal.graph.ExplorableGraph;

import java.util.List;

/**
 * Defines the framework for graph traversal algorithms.
 */
public interface GraphTraversal {

    /**
     * Traverses the graph starting from the specified vertex.
     */
    void traverse(ExplorableGraph graph, Vertex startVertex);

    /**
     * Retrieves the path taken during the traversal as a list of visited vertices.
     */
    List<Vertex> getTraversalPath();

    /**
     * Resets the traversal state, including marking all vertices as unvisited.
     */
    void resetState();
}
