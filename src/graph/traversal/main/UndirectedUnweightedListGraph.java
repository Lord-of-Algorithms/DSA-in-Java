package graph.traversal.main;

import graph.traversal.UnweightedGraph;
import graph.traversal.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an undirected and unweighted graph using an adjacency list representation.
 */
public class UndirectedUnweightedListGraph implements UnweightedGraph {

    /**
     * The adjacency list representation of the graph, where each key (a {@link Vertex})
     * represents a vertex in the graph, and the value is a list of vertices adjacent to it.
     */
    private final Map<Vertex, List<Vertex>> adjacencyList;

    UndirectedUnweightedListGraph(GraphVertex[] vertices) {
        if (vertices == null || vertices.length == 0) {
            throw new IllegalArgumentException("Vertex array cannot be null or empty.");
        }

        adjacencyList = new HashMap<>();
        for (GraphVertex graphVertex : vertices) {
            if (graphVertex == null) {
                throw new IllegalArgumentException("Vertex cannot be null.");
            }
            adjacencyList.put(graphVertex, new ArrayList<>()); // Initialize adjacency list for each graphVertex
        }
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
    public Vertex findUnvisitedAdjacent(Vertex vertex) {
        List<Vertex> neighbors = adjacencyList.get(vertex);
        if (neighbors == null) {
            throw new IllegalArgumentException("Vertex does not exist in the graph");
        }

        for (Vertex neighbor : neighbors) {
            if (!neighbor.isVisited()) {
                return neighbor;
            }
        }
        return null;
    }
}
