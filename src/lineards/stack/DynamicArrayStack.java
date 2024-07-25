package lineards.stack;

import java.util.NoSuchElementException;

/**
 * A dynamic array implementation of a stack that dynamically resizes as elements are added or removed.
 */
public class DynamicArrayStack implements Stack {

    private char[] data;
    private int top;

    /**
     * Constructor to initialize the stack with an initial capacity.
     */
    public DynamicArrayStack(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Initial capacity must be at least 1");
        }
        data = new char[capacity];
        top = -1;
    }

    @Override
    public boolean isEmpty() {
        return top == -1;
    }

    @Override
    public void push(char value) {
        if (top + 1 == data.length) {
            resize(data.length * 2);  // Double the size of the array if it is full
        }
        data[++top] = value;
    }

    @Override
    public char pop() {
        if (isEmpty())
            throw new NoSuchElementException("Stack is empty");

        char value = data[top--];

        // Reduce the size of the array if it is less than 1/4 full
        if (top + 1 > 0 && top + 1 == data.length / 4) { //  The top + 1 > 0 ensures that there’s at least one element in the stack. The number of elements in the stack is actually top + 1.
            resize(data.length / 2);
        }

        return value;
    }

    @Override
    public char peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }
        return data[top];
    }

    /**
     * Resizes the internal array to the specified new capacity.
     *
     * @param newCapacity The new capacity of the array
     */
    private void resize(int newCapacity) {
        char[] newArray = new char[newCapacity];
        System.arraycopy(data, 0, newArray, 0, top + 1);
        data = newArray;
    }
}
