package lineards.linkedlist;

import java.util.NoSuchElementException;

/**
 * A doubly linked list with references to both the head and tail nodes.
 * Each node links to both its next and its previous node, so the list can be
 * traversed in either direction and a node's neighbours can be re-linked
 * directly. Keeping a tail reference makes insertion and deletion at the end
 * O(1), just like at the beginning - and unlike a singly double-ended list,
 * even deletion at the end is O(1), because the last node knows its own
 * predecessor through its prev reference.
 */
public class DoubleEndedDoublyLinkedList {

    DoublyNode head;
    private DoublyNode tail;

    /**
     * Checks whether the linked list is empty.
     *
     * @return true if the list contains no nodes, false otherwise.
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Inserts a new node with the specified value at the beginning of the list.
     */
    public void insertFirst(char value) {
        DoublyNode newNode = new DoublyNode(value);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            // Link the new node and the old first node to each other
            newNode.next = head;
            head.prev = newNode;
            // Make the new node the new head
            head = newNode;
        }
    }

    /**
     * Inserts a new node after the node with the specified predecessor value.
     *
     * @param predValue The predecessor node's value after which the new node should be inserted.
     * @param value     The value of the new node to be inserted.
     */
    public void insertAfter(char predValue, char value) {
        DoublyNode pred = head;
        while (pred != null && pred.data != predValue) {
            pred = pred.next;
        }

        if (pred == null) {
            throw new NoSuchElementException("Predecessor not found");
        }

        DoublyNode newNode = new DoublyNode(value);
        // Link the new node to both of its neighbours
        newNode.next = pred.next;
        newNode.prev = pred;
        if (pred.next != null) {
            // Re-point the successor's prev back to the new node
            pred.next.prev = newNode;
        } else {
            // Inserting after the last node: the new node becomes the tail
            tail = newNode;
        }
        // Re-point the predecessor's next to the new node
        pred.next = newNode;
    }

    /**
     * Inserts a new node with the specified value at the end of the list.
     * The tail reference makes this O(1) - no walk needed.
     */
    public void insertLast(char value) {
        DoublyNode newNode = new DoublyNode(value);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            // Link the new node back to the current last node,
            // then move the tail forward onto it
            newNode.prev = tail;
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

        if (head == tail) {
            // There is only one node
            head = null;
            tail = null;
        } else {
            // Move the head forward and clear the new first node's prev reference
            head = head.next;
            head.prev = null;
        }
    }

    /**
     * Deletes the first occurrence of a node with the specified value.
     */
    public void deleteByValue(char value) {
        if (isEmpty()) {
            throw new NoSuchElementException("The list is empty.");
        }

        DoublyNode cur = head;
        while (cur != null && cur.data != value) {
            cur = cur.next;
        }

        if (cur == null) {
            throw new NoSuchElementException("Value " + value + " not found in the list.");
        }

        if (cur == head) {
            deleteFirst();
        } else if (cur == tail) {
            deleteLast();
        } else {
            // A node in the middle: re-link its two neighbours directly to each other
            cur.prev.next = cur.next;
            cur.next.prev = cur.prev;
        }
    }

    /**
     * Deletes the last node from this list.
     * The tail reference makes this O(1) - the last node's predecessor is just
     * tail.prev, so no walk is needed.
     */
    public void deleteLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("The list is empty.");
        }

        if (head == tail) {
            // There is only one node
            head = null;
            tail = null;
        } else {
            // The last node knows its own predecessor, so step the tail
            // straight back to it and clear its forward link
            tail = tail.prev;
            tail.next = null;
        }
    }
}
