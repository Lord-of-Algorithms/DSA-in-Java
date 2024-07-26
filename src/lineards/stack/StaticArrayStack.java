package lineards.stack;

import java.util.NoSuchElementException;

/**
 * Implementation of the {@link Stack} interface using a static-sized array.
 */
public class StaticArrayStack implements Stack {

    // Array to store the elements
    private final char[] data;
    // Index of the top element
    private int top;

    /**
     * Constructor to initialize the stack with a given capacity.
     *
     * @param capacity The maximum number of items that the stack can hold.
     */
    public StaticArrayStack(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be at least 1");
        }
        data = new char[capacity];
        top = -1;
    }

    @Override
    public boolean isEmpty() {
        return top == -1;
    }

    /**
     * Adds a character to the top of the stack.
     *
     * @param value The character to be pushed onto the stack.
     * @throws IllegalStateException if the stack is full.
     */
    @Override
    public void push(char value) {
        if (isFull()) {
            throw new IllegalStateException("Stack is full");
        }
        // Increment top and add element
        data[++top] = value;
    }

    @Override
    public char pop() {
        if (isEmpty())
            throw new NoSuchElementException("Stack is empty");
        // Decrement top which causes the
        // element to be removed
        return data[top--];
    }

    @Override
    public char peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return data[top];
    }

    /**
     * Checks if the stack is full.
     */
    private boolean isFull() {
        return top == data.length - 1;
    }
}
