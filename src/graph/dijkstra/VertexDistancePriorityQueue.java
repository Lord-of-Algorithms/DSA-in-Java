package graph.dijkstra;

import graph.Vertex;

import java.util.NoSuchElementException;

/**
 * A priority queue specifically designed for Dijkstra's algorithm that manages vertices according to their distances.
 * This implementation is simple and primarily for educational purposes to demonstrate the management of vertices in a
 * non-optimized priority queue. The queue maintains vertices in order of their distance from the source.
 * Each time a vertex is removed, it ensures the vertex with the smallest distance is selected next.
 */
class VertexDistancePriorityQueue {

    /**
     * Inner class to hold vertex and its associated distance. This helps manage the mapping of vertices
     * to their current shortest distances as known during the execution of Dijkstra's algorithm.
     */
    private static class VertexDistance {
        public final Vertex vertex;
        final int distance;

        private VertexDistance(Vertex vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }

    private final int maxSize;
    private final VertexDistance[] vertexDistances;
    private int currentSize;

    /**
     * Constructs a priority queue with a specified maximum capacity.
     *
     * @param maxSize the maximum number of elements the queue can hold
     * @throws IllegalArgumentException if the specified maximum size is less than or equal to zero
     */
    VertexDistancePriorityQueue(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("Maximum size must be greater than 0");
        }
        this.maxSize = maxSize;
        vertexDistances = new VertexDistance[maxSize];
    }

    /**
     * Checks if the queue is empty.
     *
     * @return true if the queue has no elements, false otherwise
     */
    boolean isEmpty() {
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
     * Adds a vertex along with its distance to the queue in a sorted order based on the distance.
     * If the queue is full, an IllegalStateException is thrown.
     *
     * @param vertex   the vertex to add
     * @param distance the distance of the vertex from the source
     * @throws IllegalStateException if the queue is full
     */
    void add(Vertex vertex, int distance) {
        if (isFull()) {
            throw new IllegalStateException("Queue is full");
        }
        int i;
        for (i = 0; i < currentSize; i++) {
            if (vertexDistances[i].distance <= distance) {
                // Break when find smaller distance
                break;
            }
        }
        // Move elements with smaller weight right on one position
        System.arraycopy(vertexDistances, i, vertexDistances, i + 1, currentSize - i);
        vertexDistances[i] = new VertexDistance(vertex, distance);
        currentSize++;
    }

    /**
     * Polls and returns the vertex from the queue that has the smallest distance.
     * If the queue is empty, a NoSuchElementException is thrown.
     *
     * @return the vertex with the smallest distance
     * @throws NoSuchElementException if the queue is empty
     */
    Vertex pollSmallest() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return vertexDistances[--currentSize].vertex;
    }

    /**
     * Updates the distance of a specific vertex in the queue. If the vertex is not found,
     * a NoSuchElementException is thrown.
     *
     * @param vertex      the vertex whose distance needs to be updated
     * @param newDistance the new distance of the vertex
     * @throws NoSuchElementException if the vertex is not found in the queue
     */
    void update(Vertex vertex, int newDistance) {
        int index = -1;
        for (int i = 0; i < currentSize; i++) {
            if (vertexDistances[i].vertex.equals(vertex)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new NoSuchElementException("Vertex not found");
        }
        // Removing the old vertexDistance
        System.arraycopy(vertexDistances, index + 1, vertexDistances, index, currentSize - index - 1);
        currentSize--;
        // Adding the new vertex-distance pair in sorted order
        add(vertex, newDistance);
    }
}
