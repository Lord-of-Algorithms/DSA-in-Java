package lineards.stack;

/**
 * Demonstrates the functionalities of different stack implementations.
 */
public class StackMain {

    public static void main(String[] args) {
        // Demo for StaticArrayStack
        demoStaticArrayStack();

        // Demo for DynamicArrayStack
        demoDynamicArrayStack();

        // Demo for LinkedListStack
        demoLinkedListStack();
    }

    private static void demoStaticArrayStack() {
        System.out.println("Demo: StaticArrayStack");
        StaticArrayStack staticStack = new StaticArrayStack(3);
        System.out.println("Push A, B, C");
        staticStack.push('A');
        staticStack.push('B');
        staticStack.push('C');

        try {
            System.out.println("Pushing D...");
            staticStack.push('D'); // This should fail because the stack is full
        } catch (IllegalStateException e) {
            System.out.println("Caught exception: " + e.getMessage());
        }

        System.out.println("Pop from stack: " + staticStack.pop()); // Should be C
        System.out.println("Peek from stack: " + staticStack.peek()); // Should be B
        System.out.println("Is empty: " + staticStack.isEmpty()); // Should be false
        staticStack.pop();
        staticStack.pop();
        System.out.println("Is empty after popping all elements: " + staticStack.isEmpty()); // Should be true
        System.out.println();
    }

    private static void demoDynamicArrayStack() {
        System.out.println("Demo: DynamicArrayStack");
        DynamicArrayStack dynamicStack = new DynamicArrayStack(2);
        System.out.println("Push W, X, Y, Z");
        dynamicStack.push('W');
        dynamicStack.push('X');
        dynamicStack.push('Y'); // Should resize and allow pushing another element
        dynamicStack.push('Z');

        System.out.println("Pop from stack: " + dynamicStack.pop()); // Should be Z
        System.out.println("Peek from stack: " + dynamicStack.peek()); // Should be Y
        System.out.println("Is empty: " + dynamicStack.isEmpty()); // Should be false
        dynamicStack.pop();
        dynamicStack.pop();
        dynamicStack.pop();
        System.out.println("Is empty after popping all elements: " + dynamicStack.isEmpty()); // Should be true
        System.out.println();
    }

    private static void demoLinkedListStack() {
        System.out.println("Demo: LinkedListStack");
        LinkedListStack linkedListStack = new LinkedListStack();
        System.out.println("Push 1, 2, 3");
        linkedListStack.push('1');
        linkedListStack.push('2');
        linkedListStack.push('3');

        System.out.println("Pop from stack: " + linkedListStack.pop()); // Should be 3
        System.out.println("Peek from stack: " + linkedListStack.peek()); // Should be 2
        System.out.println("Is empty: " + linkedListStack.isEmpty()); // Should be false
        linkedListStack.pop();
        linkedListStack.pop();
        System.out.println("Is empty after popping all elements: " + linkedListStack.isEmpty()); // Should be true
        System.out.println();
    }
}
