package lineards.linkedlist;

/**
 * Demonstrates the functionality of the LinkedList and DoubleEndedLinkedList classes.
 */
public class LinkedListsMain {
    public static void main(String[] args) {
        demoLinkedList();
        demoDoubleLinkedList();
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

    private static void demoDoubleLinkedList() {
        System.out.println("\nDemo DoubleLinkedList:");
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

    // Helper method to print the contents of the linked list
    private static void printList(Node head) {
        Node current = head;
        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }
}
