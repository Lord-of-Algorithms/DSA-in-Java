package graph.traversal.main;

import graph.traversal.UnweightedGraph;
import graph.traversal.Vertex;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an undirected and unweighted graph using an adjacency matrix for storing edges.
 */
class UndirectedUnweightedMatrixGraph implements UnweightedGraph {

    // Maps each vertex to its corresponding index in the adjacency matrix.
    private final Map<Vertex, Integer> indicesMap;
    // Represents the edges between vertices.
    private final int[][] adjacencyMatrix;

    UndirectedUnweightedMatrixGraph(GraphVertex[] vertices) {
        if (vertices == null || vertices.length == 0) {
            throw new IllegalArgumentException("Vertex array cannot be null or empty.");
        }
        indicesMap = new HashMap<>();
        int count = vertices.length;
        for (int i = 0; i < count; i++) {
            if (vertices[i] == null) {
                throw new IllegalArgumentException("Vertex at index " + i + " is null.");
            }
            indicesMap.put(vertices[i], i);
        }
        adjacencyMatrix = new int[count][count];
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
    public Vertex findUnvisitedAdjacent(Vertex vertex) {
        Integer index = indicesMap.get(vertex);
        if (index == null) {
            throw new IllegalArgumentException("Vertex does not exist in the graph");
        }
        for (int i = 0; i < adjacencyMatrix[index].length; i++) {
            if (adjacencyMatrix[index][i] == 1) { // Check if there's an edge
                Vertex adjacentVertex = GetVertexByIndex(indicesMap, i); // Find the vertex by its index
                if (adjacentVertex != null && !adjacentVertex.isVisited()) {
                    return adjacentVertex;
                }
            }
        }
        return null;
    }

    // Helper method to find a vertex by its index
    private static Vertex GetVertexByIndex(Map<Vertex, Integer> map, Integer value) {
        for (Map.Entry<Vertex, Integer> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
