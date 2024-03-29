package graph.traversal;


/**
 * Represents an unweighted graph capable of adding edges and finding adjacent vertices.
 */
public interface UnweightedGraph {

    /**
     * Adds an unweighted edge between two vertices in the graph.
     *
     * @param source      The source vertex of the edge.
     * @param destination The destination vertex of the edge.
     */
    void setEdge(Vertex source, Vertex destination);

    /**
     * Finds an unvisited adjacent vertex to the given vertex.
     *
     * @param vertex The vertex for which to find an unvisited adjacent vertex.
     * @return An unvisited adjacent {@link Vertex} vertex if one exists, or null
     * if all adjacent vertices have been visited or there are no adjacent vertices.
     */
    Vertex findUnvisitedAdjacent(Vertex vertex);
}
