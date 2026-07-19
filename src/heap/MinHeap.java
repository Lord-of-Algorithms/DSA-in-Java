package heap;

import java.util.NoSuchElementException;

/**
 * A binary <b>min-heap</b> backed by a fixed-capacity array.
 *
 * <p>A heap is a <i>complete</i> binary tree — every level is full except
 * possibly the last, which fills left to right — that satisfies the
 * <b>heap property</b>: every parent is less than or equal to its children.
 * As a result the smallest value is always at the root.
 *
 * <p>Because the tree is complete it maps directly onto an array with no
 * gaps, so no node objects or pointers are needed. For the node at index
 * {@code i}:
 * <ul>
 *   <li>its parent is at {@code (i - 1) / 2},</li>
 *   <li>its left child at {@code 2 * i + 1},</li>
 *   <li>its right child at {@code 2 * i + 2}.</li>
 * </ul>
 *
 * <p>All operations run in place. {@link #insert} and {@link #extractMin}
 * are {@code O(log n)}; {@link #peekMin} is {@code O(1)}.
 */
public class MinHeap {

    private final int[] heap;
    private int size;

    /**
     * Creates an empty heap that can hold up to {@code capacity} values.
     *
     * @throws IllegalArgumentException if {@code capacity} is not positive
     */
    public MinHeap(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        heap = new int[capacity];
        size = 0;
    }

    /**
     * Returns {@code true} if the heap holds no values.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns {@code true} if the heap has no room for another value.
     */
    public boolean isFull() {
        return size == heap.length;
    }

    /**
     * Returns the number of values currently in the heap.
     */
    public int size() {
        return size;
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int leftChild(int i) {
        return 2 * i + 1;
    }

    private int rightChild(int i) {
        return 2 * i + 2;
    }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    /**
     * Returns the smallest value without removing it.
     *
     * @throws NoSuchElementException if the heap is empty
     */
    public int peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        return heap[0];
    }

    /**
     * Inserts a value. The value is placed at the first free slot and then
     * sifted up until the heap property is restored.
     *
     * @throws IllegalStateException if the heap is full
     */
    public void insert(int value) {
        if (isFull()) {
            throw new IllegalStateException("Heap is full");
        }
        heap[size] = value;
        siftUp(size);
        size++;
    }

    // Moves the node at index i up while it is smaller than its parent.
    private void siftUp(int i) {
        while (i > 0 && heap[i] < heap[parent(i)]) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    /**
     * Removes and returns the smallest value. The root is swapped with the
     * last leaf, that leaf is dropped, and the new root is sifted down until
     * the heap property is restored.
     *
     * @throws NoSuchElementException if the heap is empty
     */
    public int extractMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        swap(0, size - 1);
        int min = heap[size - 1];
        size--;
        if (!isEmpty()) {
            siftDown(0);
        }
        return min;
    }

    // Moves the node at index i down while it is larger than its smaller
    // child. A node with no left child is a leaf, so the sift stops there.
    private void siftDown(int i) {
        while (leftChild(i) < size) {
            int smaller = leftChild(i);
            int right = rightChild(i);
            if (right < size && heap[right] < heap[smaller]) {
                smaller = right;
            }
            if (heap[i] <= heap[smaller]) {
                break;
            }
            swap(i, smaller);
            i = smaller;
        }
    }

    /**
     * Removes the value at index {@code i}. The last leaf fills the gap and
     * is then sifted up or down, depending on how it compares with its new
     * parent, until the heap property is restored.
     *
     * @throws IndexOutOfBoundsException if {@code i} is not a valid index
     */
    public void delete(int i) {
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException("No node at index " + i);
        }
        swap(i, size - 1);
        size--;
        if (i == size) {
            return; // removed the last leaf
        }
        if (i > 0 && heap[i] < heap[parent(i)]) {
            siftUp(i);
        } else {
            siftDown(i);
        }
    }
}
