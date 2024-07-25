package lineards.queue;

import java.util.NoSuchElementException;

/**
 * Implements a queue that automatically adjusts its capacity based on the number of elements.
 */
public class DynamicArrayQueue implements Queue {
    private char[] data;
    private int front;
    private int rear;
    private int size;

    public DynamicArrayQueue(int capacity) {
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
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Adds a character to the rear of the queue. Resizes if full.
     *
     * @param value The character to add.
     */
    @Override
    public void enqueue(char value) {
        if (size == data.length) {
            // Double the size of the array when
            // the queue is full
            resize(2 * data.length);
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
    @Override
    public char dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        char value = data[front];
        front = (front + 1) % data.length;
        size--;

        // Reduce the size of the array when the queue is 1/4 full
        if (size > 0 && size == data.length / 4) {
            resize(Math.max(data.length / 2, 10)); // Ensure the capacity doesn't get too small
        }

        return value;
    }

    /**
     * Returns the character at the front of the queue without removing it.
     */
    @Override
    public char peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return data[front];
    }

    /**
     * Resizes the underlying array holding the elements of the queue.
     *
     * @param newCapacity The new capacity for the queue.
     */
    private void resize(int newCapacity) {
        char[] newData = new char[newCapacity];
        for (int i = 0; i < size; i++) {
            newData[i] = data[(front + i) % data.length];
        }
        data = newData;
        front = 0;
        rear = size - 1;
    }
}
