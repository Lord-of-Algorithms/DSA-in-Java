package graph.traversal.main;

import graph.traversal.UnweightedGraph;

/**
 * Factory for creating instances of {@link UnweightedGraph} based on the specified graph representation.
 */
class GraphFactory {
    static UnweightedGraph createGraph(GraphRepresentation representation, GraphVertex[] vertices) {
        switch (representation) {
            case MATRIX:
                return new UndirectedUnweightedMatrixGraph(vertices);
            case LIST:
                return new UndirectedUnweightedListGraph(vertices);
            default:
                throw new IllegalArgumentException("Unknown representation method: " + representation);
        }
    }
}
