package lineards.queue;

import java.util.NoSuchElementException;

/**
 * A queue implementation using a singly linked list. This implementation allows for dynamic
 * memory allocation, growing as needed based on the queue's usage. It does not impose a fixed capacity.
 */
public class LinkedListQueue implements Queue {

    /**
     * Node in a linked list, holding a character and a reference to the next node.
     */
    private static class Node {
        final char data;
        Node next;

        Node(char data) {
            this.data = data;
        }
    }

    private Node front;
    private Node rear;

    /**
     * Checks if the queue is empty.
     *
     * @return true if the queue has no elements, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return front == null;
    }

    /**
     * Adds a character to the rear of the queue.
     *
     * @param value The character to add.
     */
    @Override
    public void enqueue(char value) {
        Node newNode = new Node(value);
        if (isEmpty()) {
            front = newNode;
        } else {
            rear.next = newNode;
        }
        rear = newNode;
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
        char data = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        return data;
    }

    /**
     * Returns the character at the front of the queue without removing it.
     */
    @Override
    public char peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        return front.data;
    }
}
