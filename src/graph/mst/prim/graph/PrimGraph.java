package graph.mst.prim.graph;

import graph.Edge;
import graph.Vertex;
import graph.WeightedGraph;

import java.util.List;

/**
 * Extends the functionality of a basic weighted graph to provide methods
 * specifically useful for implementing Prim's algorithm.
 */
public interface PrimGraph extends WeightedGraph {
    /**
     * Retrieves a list of all edges originating from a specified vertex.
     */
    List<Edge> getEdgesForSource(Vertex source);

    /**
     * Checks if a specific vertex is part of the graph.
     *
     * @return true if the vertex is present in the graph, false otherwise
     */
    boolean containsVertex(Vertex vertex);

    /**
     * Returns the total number of vertices in the graph.
     */
    int getVertexCount();
}
