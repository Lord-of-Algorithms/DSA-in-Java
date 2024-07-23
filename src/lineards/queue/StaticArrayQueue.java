package lineards.queue;

import java.util.NoSuchElementException;

/**
 * Represents a fixed-size queue implemented using a static array.
 * The queue uses a circular array mechanism to manage elements without
 * shifting them physically in the array. This allows for O(1) complexity for enqueue
 * and dequeue operations.
 */
public class StaticArrayQueue implements Queue {
    private final char[] data; // Array to store queue elements.
    private int front;
    private int rear;
    private int size;

    /**
     * Constructs a new Queue with a specified capacity.
     */
    public StaticArrayQueue(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be at least 1");
        }
        data = new char[capacity];
        front = 0;
        rear = -1;
        size = 0;
    }

    /**
     * Checks if the queue is empty.
     *
     * @return true if the queue has no elements, false otherwise.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Checks if the queue is full.
     *
     * @return true if the queue has reached its current capacity, false otherwise.
     */
    public boolean isFull() {
        return size == data.length;
    }

    /**
     * Adds a character to the rear of the queue.
     *
     * @param value The character to add.
     */
    public void enqueue(char value) {
        if (isFull()) {
            throw new IllegalStateException("Queue is full");
        }
        rear = (rear + 1) % data.length;
        data[rear] = value;
        size++;
    }

    /**
     * Removes and returns the character at the front of the queue.
     *
     * @return The character at the front of the queue.
     * @throws NoSuchElementException if the queue is empty.
     */
    public char dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        char value = data[front];
        front = (front + 1) % data.length;
        size--;
        return value;
    }

    /**
     * Returns the character at the front of the queue without removing it.
     */
    public char peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return data[front];
    }
}
