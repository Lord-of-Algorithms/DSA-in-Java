package graph.dijkstra.main;

import graph.GraphRepresentation;
import graph.Vertex;
import graph.dijkstra.graph.DijkstraGraph;
import graph.dijkstra.graph.DijkstraListGraph;
import graph.dijkstra.graph.DijkstraMatrixGraph;

class GraphFactory {
    private GraphFactory() {
        // Prevents instantiation
    }

    /**
     * Creates a graph based on the specified representation and initializes it with given vertices.
     */
    static DijkstraGraph createGraph(GraphRepresentation representation, Vertex[] vertices) {
        DijkstraGraph graph;
        switch (representation) {
            case MATRIX:
                graph = new DijkstraMatrixGraph(vertices.length);
                break;
            case LIST:
                graph = new DijkstraListGraph();
                break;
            default:
                throw new IllegalArgumentException("Unknown representation method: " + representation);
        }
        for (Vertex vertex : vertices) {
            graph.addVertex(vertex);
        }
        return graph;
    }
}
