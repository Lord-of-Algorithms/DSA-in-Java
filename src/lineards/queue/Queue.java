package lineards.queue;

import java.util.NoSuchElementException;

/**
 * Defines the interface for a queue.
 */
interface Queue {
    /**
     * Checks if the queue is empty.
     *
     * @return true if the queue has no elements, false otherwise.
     */
    boolean isEmpty();

    /**
     * Adds a character to the rear of the queue.
     *
     * @param value The character to add.
     */
    void enqueue(char value);

    /**
     * Removes and returns the character at the front of the queue.
     *
     * @return The character at the front of the queue.
     * @throws NoSuchElementException if the queue is empty.
     */
    char dequeue();

    /**
     * Returns the character at the front of the queue without removing it.
     */
    char peek();
}
