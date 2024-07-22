package lineards.linkedlist;

import java.util.NoSuchElementException;

/**
 * Represents a singly linked list with references to both the head and tail nodes.
 * This structure allows efficient insertion operations at both ends of the list,
 * making it suitable for certain types of queue implementations. Unlike a doubly linked list,
 * this list only supports unidirectional traversal from the head to the tail.
 */
public class DoubleEndedLinkedList {

    Node head;
    private Node tail;

    /**
     * Checks whether the linked list is empty.
     *
     * @return true if the list contains no nodes, false otherwise.
     */
    private boolean isEmpty() {
        return head == null;
    }

    /**
     * Inserts a new node with the specified value
     * at the start of the list
     */
    public void insertFirst(char value) {
        Node newNode = new Node(value);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            // Make the newNode point to the current head
            newNode.next = head;
            // Make the newNode as the new head
            head = newNode;
        }
    }

    /**
     * Inserts a new node after the node with
     * the specified predecessor value.
     *
     * @param predValue The predecessor node's value after which the new node should be inserted.
     * @param value     The value of the new node to be inserted.
     */
    public void insertAfter(char predValue, char value) {
        Node pred = head;
        while (pred != null && pred.data != predValue) {
            pred = pred.next;
        }

        if (pred != null) {
            Node newNode = new Node(value);
            newNode.next = pred.next;
            pred.next = newNode;
            if (pred == tail) {
                tail = newNode;
            }
        } else {
            throw new NoSuchElementException("Predecessor not found");
        }
    }

    /**
     * Inserts a new node with the specified value
     * at the end of the list
     */
    public void insertLast(char value) {
        Node newNode = new Node(value);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            // Update the tail's next reference
            // and the tail reference
            tail.next = newNode;
            tail = newNode;
        }
    }

    /**
     * Deletes the first node from this list.
     */
    public void deleteFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("The list is empty.");
        }

        // If there's only one node in the list
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            // Update the head reference to next node
            head = head.next;
        }
    }

    /**
     * Deletes the first occurrence of a node with the specified value.
     */
    public void deleteByValue(char value) {
        if (isEmpty()) {
            throw new NoSuchElementException("The list is empty.");
        }

        if (head.data == value) {
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                head = head.next;
            }
            return;
        }

        Node pred = head;
        Node temp = head.next;
        while (temp != null && temp.data != value) {
            pred = pred.next;
            temp = temp.next;
        }

        if (temp != null) {
            pred.next = temp.next;
            if (pred.next == null) {
                tail = pred;
            }
        } else {
            throw new NoSuchElementException("Value " + value + " not found in the list.");
        }
    }

    /**
     * Deletes the last node from this list.
     */
    public void deleteLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("The list is empty.");
        }

        // If there's only one node in the list
        if (head == tail) {
            head = null;
            tail = null;
        } else {
            // Find the second last node in the list
            Node pred = head;
            while (pred.next != tail) {
                pred = pred.next;
            }
            // Update the second last node's next
            // reference and the tail reference
            pred.next = null;
            tail = pred;
        }
    }
}
