package lineards.stack;

import java.util.NoSuchElementException;

/**
 * Defines the interface for a stack.
 */
interface Stack {

    /**
     * Checks if the stack is empty.
     *
     * @return true if there are no elements in the stack, false otherwise.
     */
    boolean isEmpty();

    /**
     * Adds a character to the top of the stack.
     *
     * @param value The character to be pushed onto the stack.
     */
    void push(char value);

    /**
     * Removes and returns the character at the top of the stack.
     *
     * @return The character at the top of the stack.
     * @throws NoSuchElementException if the stack is empty.
     */
    char pop();

    /**
     * Retrieves, but does not remove, the character at the top of the stack.
     *
     * @return The character currently at the top of the stack.
     * @throws NoSuchElementException if the stack is empty.
     */
    char peek();
}
