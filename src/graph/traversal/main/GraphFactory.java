package graph.traversal.main;

import graph.GraphRepresentation;
import graph.Vertex;
import graph.traversal.graph.ExplorableGraph;
import graph.traversal.graph.ExplorableListGraph;
import graph.traversal.graph.ExplorableMatrixGraph;

/**
 * Factory for creating instances of {@link ExplorableGraph} based on the specified graph representation.
 */
class GraphFactory {
    private GraphFactory() {
        // Prevents instantiation
    }

    static ExplorableGraph createGraph(GraphRepresentation representation, Vertex[] vertices) {
        ExplorableGraph graph;
        switch (representation) {
            case MATRIX:
                graph = new ExplorableMatrixGraph(vertices.length);
                break;
            case LIST:
                graph = new ExplorableListGraph();
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
