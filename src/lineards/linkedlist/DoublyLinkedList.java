package lineards.linkedlist;

import java.util.NoSuchElementException;

/**
 * A doubly linked list with a reference to the head only.
 * Each node links to both its next and its previous node, so the list can be
 * traversed in either direction and a node's neighbours can be re-linked
 * directly. Without a tail reference, reaching the last node means walking
 * from the head, so insertion and deletion at the end are O(n).
 */
public class DoublyLinkedList {

    DoublyNode head;

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
        if (!isEmpty()) {
            // Link the new node and the old first node to each other
            newNode.next = head;
            head.prev = newNode;
        }
        // Make the new node the new head
        head = newNode;
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
        }
        // Re-point the predecessor's next to the new node
        pred.next = newNode;
    }

    /**
     * Inserts a new node with the specified value at the end of the list.
     * With only a head reference, this walks from the head to the last node.
     */
    public void insertLast(char value) {
        DoublyNode newNode = new DoublyNode(value);
        if (isEmpty()) {
            head = newNode;
            return;
        }
        // Walk to the last node
        DoublyNode pred = head;
        while (pred.next != null) {
            pred = pred.next;
        }
        // Link the new node back to the last node; it becomes the new last node
        newNode.prev = pred;
        pred.next = newNode;
    }

    /**
     * Deletes the first node from this list.
     */
    public void deleteFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("The list is empty.");
        }
        // Move the head forward and clear the new first node's prev reference
        head = head.next;
        if (head != null) {
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
        } else {
            // Re-link the predecessor over the doomed node...
            cur.prev.next = cur.next;
            if (cur.next != null) {
                // ...and the successor back, unless this was the last node
                cur.next.prev = cur.prev;
            }
        }
    }

    /**
     * Deletes the last node from this list.
     * With only a head reference, this walks from the head to the last node.
     */
    public void deleteLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("The list is empty.");
        }
        if (head.next == null) {
            // There is only one node
            head = null;
            return;
        }
        // Walk to the last node
        DoublyNode cur = head;
        while (cur.next != null) {
            cur = cur.next;
        }
        // The last node knows its own predecessor, so step straight back to it
        // and clear its forward link
        cur = cur.prev;
        cur.next = null;
    }
}
