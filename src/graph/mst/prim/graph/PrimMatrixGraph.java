package graph.mst.prim.graph;

import graph.Edge;
import graph.Vertex;
import graph.VertexUtil;

import java.util.*;

/**
 * Implements the PrimGraph interface using an adjacency
 * matrix to represent the graph.
 */
public class PrimMatrixGraph implements PrimGraph {

    private static final int NO_EDGE = Integer.MAX_VALUE;

    // Maps each vertex to its corresponding index in the adjacency matrix.
    private final Map<Vertex, Integer> indicesMap;
    // Represents the edges between vertices.
    private final int[][] adjacencyMatrix;
    private final int maxVertices;
    private int currentVertexCount;

    /**
     * Constructs a graph with a specified maximum number of vertices.
     */
    public PrimMatrixGraph(int maxVertexCount) {
        indicesMap = new HashMap<>();
        adjacencyMatrix = new int[maxVertexCount][maxVertexCount];
        maxVertices = maxVertexCount;
        currentVertexCount = 0;
        for (int i = 0; i < maxVertexCount; i++) {
            Arrays.fill(adjacencyMatrix[i], NO_EDGE);
        }
    }

    /**
     * Adds a vertex to the graph. Each vertex is indexed to
     * correspond with the adjacency matrix.
     */
    @Override
    public void addVertex(Vertex vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("Vertex cannot be null.");
        }
        if (currentVertexCount >= maxVertices) {
            throw new IllegalStateException("Maximum vertices limit reached.");
        }
        indicesMap.putIfAbsent(vertex, currentVertexCount++);
    }

    /**
     * Sets or updates the weight of the edge between the
     * given source and destination vertices.
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

        int sourceIndex = indicesMap.get(source);
        int destinationIndex = indicesMap.get(destination);

        adjacencyMatrix[sourceIndex][destinationIndex] = weight;
        adjacencyMatrix[destinationIndex][sourceIndex] = weight;
    }

    @Override
    public List<Edge> getEdgesForSource(Vertex source) {
        Integer index = indicesMap.get(source);
        if (index == null) {
            throw new IllegalArgumentException("Vertex does not exist in the graph");
        }
        List<Edge> edges = new ArrayList<>();

        for (int i = 0; i < adjacencyMatrix[index].length; i++) {
            if (adjacencyMatrix[index][i] != NO_EDGE) { // Check if there's an edge
                Vertex neighbor = VertexUtil.getVertexByIndex(indicesMap, i);
                edges.add(new Edge(source, neighbor, adjacencyMatrix[index][i]));
            }
        }
        return edges;
    }

    @Override
    public int getVertexCount() {
        return currentVertexCount;
    }

    @Override
    public boolean containsVertex(Vertex vertex) {
        return indicesMap.get(vertex) != null;
    }
}
