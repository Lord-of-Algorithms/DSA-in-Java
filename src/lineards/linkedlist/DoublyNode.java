package lineards.linkedlist;

/**
 * Node in a doubly linked list, holding a character and references to both
 * the next and the previous node.
 */
class DoublyNode {
    final char data;
    DoublyNode next;
    DoublyNode prev;

    DoublyNode(char data) {
        this.data = data;
    }
}
