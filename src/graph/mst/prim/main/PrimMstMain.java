package graph.mst.prim.main;

import graph.Edge;
import graph.GraphRepresentation;
import graph.Vertex;
import graph.VertexImpl;
import graph.mst.prim.EdgePriorityQueue;
import graph.mst.prim.graph.PrimGraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The main execution class for demonstrating the use of Prim's algorithm
 * to compute the minimum spanning tree (MST) of a graph.
 */
public class PrimMstMain {
    public static void main(String[] args) {
        // Create vertices for the graph
        Vertex a = new VertexImpl("A");
        Vertex b = new VertexImpl("B");
        Vertex c = new VertexImpl("C");
        Vertex d = new VertexImpl("D");

        // Initialize the graph using a factory method to choose the representation based on a parameter
        PrimGraph graph = GraphFactory.createGraph(GraphRepresentation.MATRIX, new Vertex[]{a, b, c, d});

        // Set edges between vertices with specified weights
        graph.setEdge(a, b, 1);
        graph.setEdge(d, b, 2);
        graph.setEdge(b, c, 3);
        graph.setEdge(a, d, 4);
        graph.setEdge(d, c, 5);

        List<Edge> mst = buildMst(graph, a);
        System.out.println("MST: " + mst);

        // Modify an edge's weight and recompute the MST
        graph.setEdge(a, b, 6); // Increased weight A to B
        mst = buildMst(graph, a);
        System.out.println("MST after changing weight:  " + mst);
    }

    /**
     * Constructs the minimum spanning tree (MST) for a graph starting from a specified vertex.
     * This method implements Prim's algorithm, which incrementally builds the MST by selecting
     * the smallest weight edge leading to a vertex not yet in the MST.
     *
     * @param graph       The graph on which to build the MST.
     * @param startVertex The starting vertex for the MST.
     * @throws IllegalArgumentException if the start vertex is not part of the graph.
     * @throws IllegalStateException    if the graph is disconnected and cannot form an MST.
     */
    private static List<Edge> buildMst(PrimGraph graph, Vertex startVertex) {
        if (!graph.containsVertex(startVertex)) {
            throw new IllegalArgumentException("Start vertex is not part of the graph.");
        }

        // It is used for checking if a vertex is already in the MST
        Set<Vertex> inTreeSet = new HashSet<>();
        List<Edge> mst = new ArrayList<>();

        int vertexCount = graph.getVertexCount();
        if (vertexCount < 1) {
            throw new IllegalStateException("Graph must contain at least one vertex.");
        }

        EdgePriorityQueue pQueue = new EdgePriorityQueue(vertexCount);
        Vertex currentVertex = startVertex;

        while (inTreeSet.size() < vertexCount - 1) {
            inTreeSet.add(currentVertex);

            List<Edge> edges = graph.getEdgesForSource(currentVertex);
            for (Edge currentEdge : edges) {
                Vertex neighbour = currentEdge.getDestination();
                if (!inTreeSet.contains(neighbour)) {
                    Edge pqEdge = pQueue.findEdgeWithDestination(neighbour);
                    if (pqEdge != null) {
                        if (pqEdge.getWeight() > currentEdge.getWeight()) {
                            pQueue.replace(pqEdge, currentEdge);
                        }
                    } else {
                        pQueue.add(currentEdge);
                    }
                }
            }

            if (pQueue.isEmpty()) {
                throw new IllegalStateException("Graph is disconnected, MST cannot be completed.");
            }
            Edge edge = pQueue.pollSmallest();
            currentVertex = edge.getDestination();
            mst.add(edge);
        }

        return mst;
    }
}
