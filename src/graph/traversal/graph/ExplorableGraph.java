package graph.traversal.graph;

import graph.UnweightedGraph;
import graph.Vertex;

import java.util.List;

/**
 * Represents an unweighted graph capable of finding neighbor vertices.
 */
public interface ExplorableGraph extends UnweightedGraph {

    /**
     * Retrieves a list of neighboring vertices to a specified vertex. Neighbors are
     * those vertices that are directly connected by an edge from the specified vertex.
     */
    List<Vertex> getNeighbors(Vertex vertex);
}
