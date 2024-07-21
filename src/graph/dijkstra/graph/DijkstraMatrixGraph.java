package graph.dijkstra.graph;

import graph.Vertex;
import graph.VertexUtil;

import java.util.*;

/**
 * Implements the DijkstraGraph interface using an adjacency matrix for efficient edge weight lookups.
 */
public class DijkstraMatrixGraph implements DijkstraGraph {

    private static final int NO_EDGE = INFINITY;

    // Maps each vertex to its corresponding index in the adjacency matrix.
    private final Map<Vertex, Integer> indicesMap;
    // Represents the edges between vertices.
    private final int[][] adjacencyMatrix;
    private final int maxVertices;
    private final List<Vertex> vertices;

    /**
     * Constructs a new DijkstraMatrixGraph with a specified maximum number of vertices.
     * <p>
     * The constructor ensures that all matrix entries are set to INFINITY initially,
     * indicating that no edges are present.
     *
     * @param maxVertexCount the maximum number of vertices that the graph can accommodate.
     * @throws IllegalArgumentException if the specified maxVertexCount is less than or equal to zero,
     *                                  indicating an invalid size for the graph.
     */
    public DijkstraMatrixGraph(int maxVertexCount) {
        indicesMap = new HashMap<>();
        adjacencyMatrix = new int[maxVertexCount][maxVertexCount];
        maxVertices = maxVertexCount;
        vertices = new ArrayList<>();
        for (int i = 0; i < maxVertexCount; i++) {
            Arrays.fill(adjacencyMatrix[i], NO_EDGE); // Initialize all edges to NO_EDGE indicating no direct paths
        }
    }

    /**
     * Adds a vertex to the graph. This method assigns an index to the vertex which is used in the adjacency matrix.
     * If the vertex is already present, it does not add it again.
     *
     * @param vertex the vertex to be added to the graph
     * @throws IllegalArgumentException if the vertex is null, indicating an attempt to add an invalid vertex.
     * @throws IllegalStateException    if adding the vertex would exceed the maximum number of vertices allowed in the graph.
     */
    @Override
    public void addVertex(Vertex vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("Vertex cannot be null.");
        }
        if (vertices.size() >= maxVertices) {
            throw new IllegalStateException("Maximum vertices limit reached.");
        }
        if (!indicesMap.containsKey(vertex)) {
            indicesMap.put(vertex, vertices.size());
            vertices.add(vertex);
        }
    }

    /**
     * Adds a directed edge from a source vertex to a destination vertex with a specified weight.
     * This method updates the adjacency matrix to reflect the weight of the edge between the two vertices.
     *
     * @param source      the source vertex from which the edge is directed
     * @param destination the destination vertex towards which the edge is directed
     * @param weight      the weight of the edge, which must be non-negative
     * @throws IllegalArgumentException if either source or destination vertex is null, if both vertices are the same,
     *                                  if one or both vertices do not exist in the graph, or if the weight is negative.
     */
    @Override
    public void setEdge(Vertex source, Vertex destination, int weight) {
        if (source == null || destination == null) {
            throw new IllegalArgumentException("Source or destination vertex cannot be null.");
        }
        if (source == destination) {
            throw new IllegalArgumentException("Cannot add an edge from a vertex to itself.");
        }
        if (!indicesMap.containsKey(source)) {
            throw new IllegalArgumentException("Source vertex does not exist in the graph.");
        }
        if (!indicesMap.containsKey(destination)) {
            throw new IllegalArgumentException("Destination vertex does not exist in the graph.");
        }

        if (weight < 0) {
            throw new IllegalArgumentException("Edge weight cannot be negative.");
        }

        int sourceIndex = indicesMap.get(source);
        int destinationIndex = indicesMap.get(destination);

        adjacencyMatrix[sourceIndex][destinationIndex] = weight;
    }

    @Override
    public List<Vertex> getVertices() {
        return Collections.unmodifiableList(vertices);
    }

    @Override
    public List<Vertex> getNeighbors(Vertex vertex) {
        Integer index = indicesMap.get(vertex);
        if (index == null) {
            throw new IllegalArgumentException("Vertex does not exist in the graph");
        }
        List<Vertex> neighbors = new ArrayList<>();

        for (int i = 0; i < adjacencyMatrix[index].length; i++) {
            if (adjacencyMatrix[index][i] != NO_EDGE) { // Check if there's an edge
                Vertex neighbor = VertexUtil.getVertexByIndex(indicesMap, i);
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }

    @Override
    public int getEdgeWeightBetween(Vertex source, Vertex destination) {
        int sourceIndex = indicesMap.get(source);
        int destinationIndex = indicesMap.get(destination);
        return adjacencyMatrix[sourceIndex][destinationIndex];
    }

    @Override
    public int getVertexCount() {
        return vertices.size();
    }
}
