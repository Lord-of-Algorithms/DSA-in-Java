package graph.traversal;

/**
 * Represents a vertex that can be visited during traversal.
 */
public interface Vertex {

    /**
     * Marks this vertex as visited.
     */
    void visit();


    /**
     * Checks if this vertex has been visited.
     *
     * @return true if the vertex has been visited, false otherwise.
     */
    boolean isVisited();

    /**
     * Resets the visited status of this vertex, marking it as unvisited.
     */
    void resetVisitedStatus();
}
