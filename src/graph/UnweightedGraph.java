package graph;


/**
 * Represents an unweighted graph capable of adding edges.
 */
public interface UnweightedGraph extends Graph {
    /**
     * Adds an unweighted edge between two vertices in the graph.
     *
     * @param source      The source vertex of the edge.
     * @param destination The destination vertex of the edge.
     */
    void setEdge(Vertex source, Vertex destination);
}