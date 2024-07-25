package lineards.stack;

import java.util.NoSuchElementException;

/**
 * Implements the Stack interface using a singly linked list.
 * This implementation uses a custom Node class to represent each element in the stack,
 * allowing dynamic memory allocation as elements are added or removed.
 */
public class LinkedListStack implements Stack {

    /**
     * Node represents an element in the stack,
     * containing a value and reference to the next Node.
     */
    private static class Node {
        final char data;
        Node next;

        /**
         * Constructs a new Node with the given data value.
         */
        Node(char data) {
            this.data = data;
        }
    }

    // Top element of the stack
    private Node top;

    @Override
    public boolean isEmpty() {
        return top == null;
    }

    @Override
    public void push(char value) {
        // Create a node
        Node node = new Node(value);
        // Make the new node the top element
        node.next = top;
        top = node;
    }

    @Override
    public char pop() {
        if (isEmpty())
            throw new NoSuchElementException("Stack is empty");
        char value = top.data;
        // Remove the top element
        top = top.next;
        return value;
    }

    @Override
    public char peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return top.data;
    }
}
