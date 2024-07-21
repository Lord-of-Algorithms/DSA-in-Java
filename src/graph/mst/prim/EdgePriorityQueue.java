package graph.mst.prim;

import graph.Edge;
import graph.Vertex;

import java.util.NoSuchElementException;

/**
 * A priority queue specifically designed for storing and retrieving edges based on their weights,
 * where the edge with the smallest weight is always prioritized for retrieval.
 */
public class EdgePriorityQueue {

    private final int maxSize;
    private final Edge[] edges;
    private int currentSize;

    /**
     * Constructs a priority queue with a specified maximum size.
     *
     * @param maxSize the maximum number of edges the queue can hold
     * @throws IllegalArgumentException if maxSize is less than or equal to zero
     */
    public EdgePriorityQueue(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("Maximum size must be greater than 0");
        }
        this.maxSize = maxSize;
        edges = new Edge[maxSize];
    }

    /**
     * Checks if the queue is empty.
     *
     * @return true if the queue has no elements, false otherwise
     */
    public boolean isEmpty() {
        return currentSize == 0;
    }

    /**
     * Checks if the queue is full.
     *
     * @return true if the queue is at maximum capacity, false otherwise
     */
    boolean isFull() {
        return currentSize == maxSize;
    }

    /**
     * Adds an edge to the queue in a position that maintains the order of edge weights.
     * This method ensures that the queue is sorted such that the edge with the smallest weight can be
     * retrieved first, maintaining a priority queue where smaller weights signify higher priority.
     * It is performed in O(n) time, where n is the number of edges in the queue.
     *
     * @param edge the edge to add
     * @throws IllegalStateException if the queue is full
     */
    public void add(Edge edge) {
        if (isFull()) {
            throw new IllegalStateException("Queue is full");
        }
        int i;
        for (i = 0; i < currentSize; i++) {
            if (edges[i].getWeight() <= edge.getWeight()) {
                // Break when find the edge with smaller weight
                break;
            }
        }
        // Move elements with smaller weight right on one position
        System.arraycopy(edges, i, edges, i + 1, currentSize - i);
        edges[i] = edge;
        currentSize++;
    }

    /**
     * Retrieves, but does not remove, the smallest weight edge in the queue.
     *
     * @return the smallest weight edge, or null if the queue is empty
     */
    Edge peekSmallest() {
        return currentSize == 0 ? null : edges[currentSize - 1];
    }

    /**
     * Retrieves and removes the smallest weight edge in the queue.
     *
     * @return the smallest weight edge
     * @throws NoSuchElementException if the queue is empty
     */
    public Edge pollSmallest() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return edges[--currentSize];
    }

    /**
     * Replaces one edge with another and maintains the queue's order.
     * The replacement is performed in O(n) time, where n is the number of edges in the queue
     *
     * @param target      the edge to be replaced
     * @param replacement the new edge to insert
     * @throws NoSuchElementException if the target edge is not found
     */
    public void replace(Edge target, Edge replacement) {
        int index = -1;
        for (int i = 0; i < currentSize; i++) {
            if (edges[i].equals(target)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new NoSuchElementException("Edge not found");
        }
        // Removing the old edge
        System.arraycopy(edges, index + 1, edges, index, currentSize - index - 1);
        currentSize--;
        // Adding the new edge in sorted order
        add(replacement);
    }

    /**
     * Finds an edge with a specific destination.
     * The search is performed in O(n) time, where n is the number of edges in the queue.
     *
     * @param destination the vertex destination of the edge to find
     * @return the edge with the specified destination, or null if no such edge exists
     */
    public Edge findEdgeWithDestination(Vertex destination) {
        int index = -1;
        for (int i = 0; i < currentSize; i++) {
            if (edges[i].getDestination().equals(destination)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            return edges[index];
        } else {
            return null;
        }
    }
}
