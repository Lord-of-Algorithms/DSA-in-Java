package graph.mst.prim.main;

import graph.GraphRepresentation;
import graph.Vertex;
import graph.mst.prim.graph.PrimGraph;
import graph.mst.prim.graph.PrimListGraph;
import graph.mst.prim.graph.PrimMatrixGraph;

class GraphFactory {
    private GraphFactory() {
        // Prevents instantiation
    }

    /**
     * Creates a graph based on the specified representation and initializes it with given vertices.
     */
    static PrimGraph createGraph(GraphRepresentation representation, Vertex[] vertices) {
        PrimGraph graph;
        switch (representation) {
            case MATRIX:
                graph = new PrimMatrixGraph(vertices.length);
                break;
            case LIST:
                graph = new PrimListGraph();
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
