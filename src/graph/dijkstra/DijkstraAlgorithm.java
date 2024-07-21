package graph.dijkstra;

import graph.Vertex;
import graph.dijkstra.graph.DijkstraGraph;

import java.util.*;

/**
 * Implements Dijkstra's algorithm for finding the shortest paths
 * from a single source vertex to all other vertices in a graph
 * with non-negative edge weights.
 */
public class DijkstraAlgorithm {

    private final Map<Vertex, Vertex> predecessorMap;

    public DijkstraAlgorithm() {
        predecessorMap = new HashMap<>();
    }

    /**
     * Computes the shortest paths from the specified source vertex.
     */
    public void computePaths(DijkstraGraph graph, Vertex source) {
        if (!graph.getVertices().contains(source)) {
            throw new IllegalArgumentException("Source vertex is not in the graph");
        }

        Map<Vertex, Integer> distanceFromSourceMap = new HashMap<>();
        VertexDistancePriorityQueue pQueue = new VertexDistancePriorityQueue(graph.getVertexCount());

        List<Vertex> vertices = graph.getVertices();
        for (Vertex v : vertices) {
            int initialDistance = v.equals(source) ? 0 : DijkstraGraph.INFINITY;
            distanceFromSourceMap.put(v, initialDistance);
            predecessorMap.put(v, null);
            pQueue.add(v, distanceFromSourceMap.get(v));
        }

        while (!pQueue.isEmpty()) {
            Vertex closestToSource = pQueue.pollSmallest();
            List<Vertex> neighbors = graph.getNeighbors(closestToSource);

            for (Vertex n : neighbors) {
                int currentDistance = distanceFromSourceMap.get(n);
                int edgeWeight = graph.getEdgeWeightBetween(closestToSource, n);
                // Safely compute an alternative path distance without risk of overflow
                int alternativeDistance = distanceFromSourceMap.get(closestToSource) + edgeWeight;
                if (alternativeDistance < currentDistance) {
                    pQueue.update(n, alternativeDistance);
                    distanceFromSourceMap.put(n, alternativeDistance);
                    predecessorMap.put(n, closestToSource);
                }
            }
        }
    }

    /**
     * Retrieves the shortest path from the source vertex
     * to the specified target vertex.
     */
    public List<Vertex> getShortestPathTo(Vertex target) {
        List<Vertex> path = new ArrayList<>();
        path.add(target);
        Vertex predecessor = predecessorMap.get(target);
        while (predecessor != null) {
            path.add(predecessor);
            predecessor = predecessorMap.get(predecessor);
        }
        Collections.reverse(path); // Reverse the path to get the right order
        return path;
    }

    /**
     * Resets the internal state of the algorithm, clearing stored predecessors.
     */
    public void resetState() {
        predecessorMap.clear();
    }
}
