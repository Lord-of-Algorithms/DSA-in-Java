package lineards.linkedlist;

/**
 * Demonstrates the functionality of the LinkedList and DoubleEndedLinkedList classes.
 */
public class LinkedListsMain {
    public static void main(String[] args) {
        demoLinkedList();
        demoDoubleEndedLinkedList();
        demoDoublyLinkedList();
        demoDoubleEndedDoublyLinkedList();
    }

    private static void demoLinkedList() {
        System.out.println("Demo LinkedList:");
        LinkedList linkedList = new LinkedList();
        linkedList.insertFirst('C');
        linkedList.insertFirst('B');
        linkedList.insertFirst('A');
        System.out.println("Inserting C, B, A at the start...");
        printList(linkedList.head);

        linkedList.insertLast('D');
        System.out.println("Inserting D at the end...");
        printList(linkedList.head);

        linkedList.insertAfter('B', 'E');
        System.out.println("Inserting E after B...");
        printList(linkedList.head);

        linkedList.deleteFirst();
        System.out.println("Deleting the first element...");
        printList(linkedList.head);

        linkedList.deleteByValue('E');
        System.out.println("Deleting E...");
        printList(linkedList.head);

        linkedList.deleteLast();
        System.out.println("Deleting the last element...");
        printList(linkedList.head);
    }

    private static void demoDoubleEndedLinkedList() {
        System.out.println("\nDemo DoubleEndedLinkedList:");
        DoubleEndedLinkedList doubleEndedLinkedList = new DoubleEndedLinkedList();
        doubleEndedLinkedList.insertFirst('1');
        doubleEndedLinkedList.insertFirst('2');
        doubleEndedLinkedList.insertFirst('3');
        System.out.println("Inserting 1, 2, 3 at the start...");
        printList(doubleEndedLinkedList.head);

        doubleEndedLinkedList.insertLast('4');
        System.out.println("Inserting 4 at the end...");
        printList(doubleEndedLinkedList.head);

        doubleEndedLinkedList.insertAfter('2', '5');
        System.out.println("Inserting 5 after 2...");
        printList(doubleEndedLinkedList.head);

        doubleEndedLinkedList.deleteFirst();
        System.out.println("Deleting the first element...");
        printList(doubleEndedLinkedList.head);

        doubleEndedLinkedList.deleteByValue('5');
        System.out.println("Deleting 5...");
        printList(doubleEndedLinkedList.head);

        doubleEndedLinkedList.deleteLast();
        System.out.println("Deleting the last element...");
        printList(doubleEndedLinkedList.head);
    }

    private static void demoDoublyLinkedList() {
        System.out.println("\nDemo DoublyLinkedList:");
        DoublyLinkedList doublyLinkedList = new DoublyLinkedList();
        doublyLinkedList.insertFirst('C');
        doublyLinkedList.insertFirst('B');
        doublyLinkedList.insertFirst('A');
        System.out.println("Inserting C, B, A at the start...");
        printList(doublyLinkedList.head);

        doublyLinkedList.insertLast('D');
        System.out.println("Inserting D at the end...");
        printList(doublyLinkedList.head);

        doublyLinkedList.insertAfter('B', 'E');
        System.out.println("Inserting E after B...");
        printList(doublyLinkedList.head);

        doublyLinkedList.deleteFirst();
        System.out.println("Deleting the first element...");
        printList(doublyLinkedList.head);

        doublyLinkedList.deleteByValue('E');
        System.out.println("Deleting E...");
        printList(doublyLinkedList.head);

        doublyLinkedList.deleteLast();
        System.out.println("Deleting the last element...");
        printList(doublyLinkedList.head);
    }

    private static void demoDoubleEndedDoublyLinkedList() {
        System.out.println("\nDemo DoubleEndedDoublyLinkedList:");
        DoubleEndedDoublyLinkedList list = new DoubleEndedDoublyLinkedList();
        list.insertFirst('C');
        list.insertFirst('B');
        list.insertFirst('A');
        System.out.println("Inserting C, B, A at the start...");
        printList(list.head);

        list.insertLast('D');
        System.out.println("Inserting D at the end...");
        printList(list.head);

        list.insertAfter('B', 'E');
        System.out.println("Inserting E after B...");
        printList(list.head);

        list.deleteFirst();
        System.out.println("Deleting the first element...");
        printList(list.head);

        list.deleteByValue('E');
        System.out.println("Deleting E...");
        printList(list.head);

        list.deleteLast();
        System.out.println("Deleting the last element...");
        printList(list.head);
    }

    // Helper method to print the contents of the linked list
    private static void printList(Node head) {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }

    // Helper method to print the contents of the doubly linked list
    private static void printList(DoublyNode head) {
        DoublyNode current = head;
        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }
}
