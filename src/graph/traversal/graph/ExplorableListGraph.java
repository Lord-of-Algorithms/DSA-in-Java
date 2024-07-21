package graph.traversal.graph;

import graph.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an undirected and unweighted graph using an adjacency list representation.
 */
public class ExplorableListGraph implements ExplorableGraph {

    /**
     * The adjacency list representation of the graph, where each key (a {@link Vertex})
     * represents a vertex in the graph, and the value is a list of vertices adjacent to it.
     */
    private final Map<Vertex, List<Vertex>> adjacencyList;

    public ExplorableListGraph() {
        adjacencyList = new HashMap<>();
    }

    @Override
    public void addVertex(Vertex vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("Vertex cannot be null.");
        }
        adjacencyList.putIfAbsent(vertex, new ArrayList<>()); // Initialize adjacency list for given vertex
    }

    @Override
    public void setEdge(Vertex source, Vertex destination) {
        if (source == null || destination == null) {
            throw new IllegalArgumentException("Source or destination vertex cannot be null.");
        }

        if (source == destination) {
            throw new IllegalArgumentException("Cannot add an edge from a vertex to itself.");
        }

        List<Vertex> sourceNeighbors = adjacencyList.get(source);
        List<Vertex> destinationNeighbors = adjacencyList.get(destination);

        if (sourceNeighbors == null || destinationNeighbors == null) {
            throw new IllegalArgumentException("One or both vertices do not exist in the graph.");
        }

        // Add the edge in both directions since the graph is undirected
        sourceNeighbors.add(destination);
        destinationNeighbors.add(source);
    }

    @Override
    public List<Vertex> getNeighbors(Vertex vertex) {
        return adjacencyList.get(vertex);
    }
}
