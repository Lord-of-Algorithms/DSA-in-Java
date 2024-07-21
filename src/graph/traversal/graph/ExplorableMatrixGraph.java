package graph.traversal.graph;

import graph.Vertex;
import graph.VertexUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents an undirected and unweighted graph using an adjacency matrix for storing edges.
 */
public class ExplorableMatrixGraph implements ExplorableGraph {

    // Maps each vertex to its corresponding index in the adjacency matrix.
    private final Map<Vertex, Integer> indicesMap;
    // Represents the edges between vertices.
    private final int[][] adjacencyMatrix;
    private final int maxVertices;
    private int currentVertexCount;

    /**
     * Constructs a graph with a specified maximum number of vertices.
     */
    public ExplorableMatrixGraph(int maxVertexCount) {
        indicesMap = new HashMap<>();
        adjacencyMatrix = new int[maxVertexCount][maxVertexCount];
        maxVertices = maxVertexCount;
        currentVertexCount = 0;
    }

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

    @Override
    public void setEdge(Vertex source, Vertex destination) {
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

        adjacencyMatrix[sourceIndex][destinationIndex] = 1;
        adjacencyMatrix[destinationIndex][sourceIndex] = 1;
    }

    @Override
    public List<Vertex> getNeighbors(Vertex vertex) {
        Integer index = indicesMap.get(vertex);
        if (index == null) {
            throw new IllegalArgumentException("Vertex does not exist in the graph");
        }
        List<Vertex> neighbors = new ArrayList<>();

        for (int i = 0; i < adjacencyMatrix[index].length; i++) {
            if (adjacencyMatrix[index][i] == 1) { // Check if there's an edge
                Vertex neighbor = VertexUtil.getVertexByIndex(indicesMap, i);
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }
}
