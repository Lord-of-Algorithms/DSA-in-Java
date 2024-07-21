package graph;

/**
 * Represents a weighted graph capable of adding edges.
 */
public interface WeightedGraph extends Graph {
    /**
     * Adds a weighted edge between two vertices in the graph.
     *
     * @param source      The source vertex of the edge.
     * @param destination The destination vertex of the edge.
     * @param weight      The weight of the edge.
     */
    void setEdge(Vertex source, Vertex destination, int weight);
}
