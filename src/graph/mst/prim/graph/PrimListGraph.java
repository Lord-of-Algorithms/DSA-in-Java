package graph.mst.prim.graph;

import graph.WeightedEdge;
import graph.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implements the PrimGraph interface using an adjacency list to represent the graph.
 */
public class PrimListGraph implements PrimGraph {

    private final Map<Vertex, List<WeightedEdge>> adjacencyList;

    public PrimListGraph() {
        adjacencyList = new HashMap<>();
    }

    /**
     * Adds a vertex to the graph. Initializes an empty list for
     * edges if the vertex is new.
     */
    @Override
    public void addVertex(Vertex vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("Vertex cannot be null.");
        }
        adjacencyList.putIfAbsent(vertex, new ArrayList<>());
    }

    /**
     * Adds or updates an edge between two specified vertices with the given weight.
     * Since the edge properties are immutable, updating an edge will involve
     * removing the old edge and adding a new one.
     */
    @Override
    public void setEdge(Vertex source, Vertex destination, int weight) {
        if (source == null || destination == null) {
            throw new IllegalArgumentException("Source or destination vertex cannot be null.");
        }

        if (source == destination) {
            throw new IllegalArgumentException("Cannot add an edge from a vertex to itself.");
        }

        if (!adjacencyList.containsKey(source) || !adjacencyList.containsKey(destination)) {
            throw new IllegalArgumentException("Both vertices must be added to the graph before connecting them.");
        }

        List<WeightedEdge> edges = adjacencyList.get(source);
        List<WeightedEdge> reverseEdges = adjacencyList.get(destination);

        // Remove existing edge if it exists and add new one with updated weight
        replaceOrUpdateEdge(edges, source, destination, weight);
        replaceOrUpdateEdge(reverseEdges, destination, source, weight);
    }

    /**
     * Replaces an existing edge between specified source and destination vertices or
     * adds a new edge if no existing edge is found.
     */
    private void replaceOrUpdateEdge(List<WeightedEdge> edgeList, Vertex source, Vertex destination, int weight) {
        WeightedEdge existingEdge = findEdge(edgeList, source, destination);
        if (existingEdge != null) {
            edgeList.remove(existingEdge); // Remove old edge since WeightedEdge properties are final
        }
        edgeList.add(new WeightedEdge(source, destination, weight)); // Add new edge with the updated weight
    }

    /**
     * Finds an edge between a specified source and destination vertex.
     */
    private WeightedEdge findEdge(List<WeightedEdge> edges, Vertex source, Vertex destination) {
        for (WeightedEdge e : edges) {
            if (e.getSource().equals(source) && e.getDestination().equals(destination)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public List<WeightedEdge> getEdgesForSource(Vertex source) {
        return adjacencyList.get(source);
    }

    @Override
    public int getVertexCount() {
        return adjacencyList.size();
    }

    @Override
    public boolean containsVertex(Vertex vertex) {
        return adjacencyList.containsKey(vertex);
    }
}
