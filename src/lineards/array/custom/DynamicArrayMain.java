package lineards.array.custom;

/**
 * Demonstrates the use of the DynamicArray class.
 */
public class DynamicArrayMain {
    public static void main(String[] args) {
        DynamicArray dynamicArray = new DynamicArray(5);

        // Adding elements to the dynamic array
        dynamicArray.add(10);
        dynamicArray.add(20);
        dynamicArray.add(30);
        System.out.println("Adding 10, 20, 30...");
        printArray(dynamicArray);

        // Inserting element at a specific index
        dynamicArray.add(1, 15);
        System.out.println("Inserting 15 at index 1...");
        printArray(dynamicArray);

        // Removing element from the array
        dynamicArray.remove(1);
        System.out.println("Removing element at index 1...");
        printArray(dynamicArray);

        // Accessing elements
        System.out.println("Element at index 0: " + dynamicArray.get(0) + "\n");

        // Adding elements to expand capacity
        dynamicArray.add(40);
        dynamicArray.add(50);
        dynamicArray.add(60); // This should trigger a resize
        System.out.println("Adding more elements to trigger resize...");
        printArray(dynamicArray);

        // Removing elements to trigger shrink
        dynamicArray.remove(0);
        dynamicArray.remove(0);
        dynamicArray.remove(0);
        dynamicArray.remove(0);
        System.out.println("Removing elements to trigger shrink...");
        printArray(dynamicArray);
    }

    /**
     * Prints all elements in the dynamic array along with its size and capacity.
     *
     * @param dynamicArray The dynamic array to print.
     */
    private static void printArray(DynamicArray dynamicArray) {
        System.out.print("Dynamic Array (Size=" + dynamicArray.size() + ", Capacity=" + dynamicArray.capacity() + "): ");
        for (int i = 0; i < dynamicArray.size(); i++) {
            System.out.print(dynamicArray.get(i) + " ");
        }
        System.out.println("\n");
    }
}
