package lineards.linkedlist;

import java.util.NoSuchElementException;

/**
 * A singly linked list implementation for storing characters.
 */
public class LinkedList {

    Node head;

    /**
     * Checks whether the linked list is empty.
     *
     * @return true if the list contains no nodes, false otherwise.
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Inserts the node with specified value
     * at the beginning of this list.
     */
    public void insertFirst(char value) {
        Node node = new Node(value);
        node.next = head;
        head = node;
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
            Node node = new Node(value);
            node.next = pred.next;
            pred.next = node;
        } else {
            throw new NoSuchElementException("Predecessor not found");
        }
    }

    /**
     * Inserts a new node with the specified value at
     * the end of this list.
     */
    public void insertLast(char value) {
        if (isEmpty()) {
            head = new Node(value);
            return;
        }
        Node pred = head;
        while (pred.next != null) {
            pred = pred.next;
        }
        pred.next = new Node(value);
    }

    /**
     * Deletes the first node from this list.
     */
    public void deleteFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("The list is empty.");
        }
        head = head.next;
    }

    /**
     * Deletes the first found node with the specified value.
     */
    public void deleteByValue(char value) {
        if (isEmpty()) {
            throw new NoSuchElementException("The list is empty.");
        }
        if (head.data == value) {
            // Delete the head
            head = head.next;
        }
        Node pred = head;
        Node temp = head.next;
        while (temp != null && temp.data != value) {
            pred = pred.next;
            temp = temp.next;
        }
        if (temp != null) {
            // The node to be deleted is found.
            // Delete it by changing references.
            pred.next = temp.next;
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
        if (head.next == null) {
            // There is only one node
            head = null;
        }
        Node pred = head;
        Node temp = head.next;
        while (temp.next != null) {
            pred = pred.next;
            temp = temp.next;
        }
        pred.next = null;
    }
}
