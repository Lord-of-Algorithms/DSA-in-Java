package lineards.array;

import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrates the use of a dynamic array (ArrayList).
 * This class showcases various operations such as insertion and deletion
 * in a dynamically sized array.
 */
public class ArrayListMain {

    public static void main(String[] args) {
        // 1. Initialization of an ArrayList with initial values.
        List<String> planets = new ArrayList<>();

        planets.add("Venus");
        planets.add("Pluto");
        planets.add("Earth");
        System.out.println("Initial values...");
        printList(planets);

        // 2. Insertion at the end.
        planets.add("Jupiter");
        System.out.println("Adding 'Jupiter' at the end...");
        printList(planets);

        // 3. Insertion at the beginning.
        planets.add(0, "Mercury");
        System.out.println("Inserting 'Mercury' at the beginning...");
        printList(planets);

        // 4. Insertion at a specified position.
        planets.add(4, "Mars");
        System.out.println("Inserting 'Mars' at position 4...");
        printList(planets);

        // 5. Deletion by index.
        planets.remove(2);
        System.out.println("Removing 'Pluto' at position 2...");
        printList(planets);

        // 6. Deletion by value.
        planets.remove("Jupiter");
        System.out.println("Removing 'Jupiter' by value...");
        printList(planets);
    }

    /**
     * Prints the current state of the list.
     *
     * @param list The list to be printed.
     */
    private static void printList(List<String> list) {
        System.out.println("Current state of the list:");
        System.out.println(list);
        System.out.println();
    }
}
