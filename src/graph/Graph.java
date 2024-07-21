package graph;

/**
 * Represents a general graph structure.
 * It serves as a base interface that other specific
 * graph interfaces and classes can extend or implement
 * to provide more complex behaviors
 * such as adding edges and querying graph properties.
 */
public interface Graph {
    /**
     * Adds a vertex to the graph.
     */
    void addVertex(Vertex vertex);
}
