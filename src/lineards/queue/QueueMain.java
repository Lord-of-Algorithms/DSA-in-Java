package lineards.queue;

/**
 * Demonstrates the functionalities of different queue implementations.
 */
public class QueueMain {

    public static void main(String[] args) {
        // Demo for StaticArrayQueue
        System.out.println("Static Array Queue Demo:");
        StaticArrayQueue staticQueue = new StaticArrayQueue(5);
        demoQueue(staticQueue);

        // Demo for DynamicArrayQueue
        System.out.println("\nDynamic Array Queue Demo:");
        DynamicArrayQueue dynamicQueue = new DynamicArrayQueue(5);
        demoQueue(dynamicQueue);

        // Demo for LinkedListQueue
        System.out.println("\nLinked List Queue Demo:");
        LinkedListQueue linkedListQueue = new LinkedListQueue();
        demoQueue(linkedListQueue);
    }

    /**
     * Demonstrates basic queue operations like enqueue, dequeue, and peek on a queue.
     *
     * @param queue The queue to demonstrate.
     */
    private static void demoQueue(Queue queue) {
        try {
            queue.enqueue('A');
            queue.enqueue('B');
            queue.enqueue('C');
            System.out.println("Enqueuing A, B, and C...");
            System.out.println("Peek: " + queue.peek());
            System.out.println("Dequeue: " + queue.dequeue());
            System.out.println("Peek: " + queue.peek());
            System.out.println("Dequeue: " + queue.dequeue());
            System.out.println("Peek: " + queue.peek());
            System.out.println("Empty: " + queue.isEmpty());
            System.out.println("Dequeue: " + queue.dequeue());
            System.out.println("Empty: " + queue.isEmpty());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
