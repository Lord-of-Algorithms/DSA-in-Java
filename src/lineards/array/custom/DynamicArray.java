package lineards.array.custom;

/**
 * A custom dynamic array implementation that automatically resizes itself as needed
 * while providing high-performance array operations. This class simulates
 * a dynamic array using a static array to store elements.
 */
public class DynamicArray {
    private int[] array;
    private int size;
    private int capacity;

    /**
     * Constructs a dynamic array with a specified initial capacity.
     *
     * @param capacity The initial capacity of the array. Must be greater than zero.
     * @throws IllegalArgumentException if the specified capacity is less than 1.
     */
    public DynamicArray(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be at least 1");
        }
        this.array = new int[capacity];
        this.size = 0;
        this.capacity = capacity;
    }

    /**
     * Returns the value at the given index.
     */
    public int get(int index) {
        checkBounds(index);
        return array[index];
    }

    /**
     * Sets the value at the given index to the specified value.
     */
    public void set(int index, int value) {
        checkBounds(index);
        array[index] = value;
    }

    /**
     * Adds a new value to the end of this dynamic array.
     */
    public void add(int value) {
        ensureCapacity();
        array[size++] = value;
    }

    /**
     * Inserts a new value at the specified index.
     */
    public void add(int index, int value) {
        checkBounds(index);
        ensureCapacity();
        // Moves all elements from the specified 'index'
        // to the end of the array one position to the
        // right in the array.
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = value;
        size++;
    }

    /**
     * Removes the value at the given index.
     */
    public void remove(int index) {
        checkBounds(index);
        int numberElementsMoved = size - index - 1;
        if (numberElementsMoved > 0) {
            // Shifts all elements after 'index' one position
            // to the left, overwriting the element at 'index'
            // and removing it.
            System.arraycopy(array, index + 1, array, index, numberElementsMoved);
        }
        size--;
        shrinkCapacity();
    }

    /**
     * Returns the number of elements in this dynamic array.
     */
    public int size() {
        return size;
    }

    /**
     * Returns the current capacity of the dynamic array.
     */
    public int capacity() {
        return capacity;
    }

    /**
     * Checks if the given index is within the bounds of the array.
     *
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    private void checkBounds(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    /**
     * Ensures that the array has sufficient capacity to store additional elements.
     */
    private void ensureCapacity() {
        if (size == capacity) {
            resize(capacity * 2);
        }
    }

    /**
     * Reduces the size of the array when the number of elements falls below a quarter of the capacity.
     */
    private void shrinkCapacity() {
        if (size <= capacity / 4 && capacity > 1) {
            resize(Math.max(capacity / 2, 1));
        }
    }

    /**
     * Adjusts the capacity of the dynamic array by
     * creating a new array with the given capacity,
     * copying the elements from the old array to the
     * new array, and updating the capacity and
     * reference to the old array.
     */
    private void resize(int newCapacity) {
        int[] newArray = new int[newCapacity];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
        capacity = newCapacity;
    }
}
