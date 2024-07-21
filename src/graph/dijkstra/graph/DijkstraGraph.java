package graph.dijkstra.graph;

import graph.Vertex;
import graph.WeightedGraph;

import java.util.List;

/**
 * Defines the interface for a graph structure that can be used with Dijkstra's algorithm.
 */
public interface DijkstraGraph extends WeightedGraph {

    // Represents a value considered as "infinite distance," used when there is no direct path between two vertices.
    // This value is set to half of Integer.MAX_VALUE to avoid arithmetic overflow when calculating distances.
    int INFINITY = Integer.MAX_VALUE / 2;

    /**
     * Retrieves a list of all vertices in the graph.
     */
    List<Vertex> getVertices();

    /**
     * Retrieves a list of neighboring vertices to a specified vertex. Neighbors are
     * those vertices that are directly connected by an edge from the specified vertex.
     */
    List<Vertex> getNeighbors(Vertex vertex);

    /**
     * Retrieves the weight of the edge between two specified vertices.
     * If no edge exists, this method returns a value that signifies
     * no connection.
     */
    int getEdgeWeightBetween(Vertex source, Vertex destination);

    /**
     * Retrieves the count of vertices in the graph.
     */
    int getVertexCount();
}
