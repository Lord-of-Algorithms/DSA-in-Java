package graph.dijkstra.graph;

import graph.Edge;
import graph.Vertex;

import java.util.*;

/**
 * Implements the DijkstraGraph interface using an adjacency list representation.
 * This class supports directed graphs and is optimized for scenarios where the graph might be sparse.
 */
public class DijkstraListGraph implements DijkstraGraph {

    private final Map<Vertex, List<Edge>> adjacencyList;
    private final List<Vertex> vertices;

    /**
     * Constructs an empty DijkstraListGraph with no vertices or edges.
     */
    public DijkstraListGraph() {
        adjacencyList = new HashMap<>();
        vertices = new ArrayList<>();
    }

    /**
     * Adds a vertex to the graph. If the vertex is already in the graph, it does not add it again.
     *
     * @param vertex the vertex to add
     * @throws IllegalArgumentException if the vertex is null
     */
    @Override
    public void addVertex(Vertex vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("Vertex cannot be null.");
        }
        if (!adjacencyList.containsKey(vertex)) {
            adjacencyList.put(vertex, new ArrayList<>());
            vertices.add(vertex);
        }
    }

    /**
     * Adds or updates a directed edge from a source vertex to a destination vertex with a specified weight.
     *
     * @param source      the source vertex of the edge
     * @param destination the destination vertex of the edge
     * @param weight      the weight of the edge
     * @throws IllegalArgumentException if either vertex is null, if the vertices are the same,
     *                                  or if one or both vertices do not exist in the graph,
     *                                  or if the weight is negative.
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
            throw new IllegalArgumentException("One or both vertices do not exist in the graph.");
        }

        if (weight < 0) {
            throw new IllegalArgumentException("Edge weight cannot be negative.");
        }

        List<Edge> edges = adjacencyList.get(source);

        // Remove existing edge if it exists and add new one with updated weight
        replaceOrUpdateEdge(edges, source, destination, weight);
    }

    /**
     * Replaces an existing edge between specified source and destination vertices or
     * adds a new edge if no existing edge is found.
     */
    private void replaceOrUpdateEdge(List<Edge> edgeList, Vertex source, Vertex destination, int weight) {
        Edge existingEdge = findEdge(edgeList, source, destination);
        if (existingEdge != null) {
            edgeList.remove(existingEdge); // Remove old edge since Edge properties are final
        }
        edgeList.add(new Edge(source, destination, weight)); // Add new edge with the updated weight
    }

    /**
     * Finds an edge between a specified source and destination vertex.
     */
    private Edge findEdge(List<Edge> edges, Vertex source, Vertex destination) {
        for (Edge e : edges) {
            if (e.getSource().equals(source) && e.getDestination().equals(destination)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public List<Vertex> getVertices() {
        return Collections.unmodifiableList(vertices);
    }

    @Override
    public List<Vertex> getNeighbors(Vertex vertex) {
        List<Vertex> neighbors = new ArrayList<>();
        List<Edge> edges = adjacencyList.get(vertex);
        for (Edge e : edges) {
            neighbors.add(e.getDestination());
        }
        return neighbors;
    }

    @Override
    public int getEdgeWeightBetween(Vertex source, Vertex destination) {
        List<Edge> edges = adjacencyList.get(source);
        for (Edge e : edges) {
            if (e.getDestination().equals(destination)) {
                return e.getWeight();
            }
        }
        return INFINITY;
    }

    @Override
    public int getVertexCount() {
        return vertices.size();
    }
}
