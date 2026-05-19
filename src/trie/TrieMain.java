package trie;

/**
 * Demonstrates {@link Trie} operations: insert, search, delete (with
 * cleanup of empty branches), and autocomplete (startsWith) on a small
 * pool of words. Output is annotated so the trace reads as a tour of
 * the four operations.
 */
public class TrieMain {

    public static void main(String[] args) {
        Trie trie = new Trie();

        System.out.println("=== Insert ===");
        // Intentionally NOT alphabetical so the output below reflects
        // the LinkedHashMap insertion-order child iteration: children
        // of each node are visited in the order they were first added.
        // See the TrieNode JavaDoc for the HashMap / TreeMap swap and
        // their order trade-offs.
        for (String word : new String[]{
                "topology", "cat", "cap", "cabin",
                "catfish", "cab", "top", "catch"}) {
            trie.insert(word);
            System.out.println("insert(\"" + word + "\")   size=" + trie.size());
        }

        System.out.println("\n=== Words ===");
        // Note: NOT alphabetical. Words come out in the order DFS visits
        // their end-of-word nodes, and the per-node child order follows
        // the insertion sequence above (cat went in before cap, so 't'
        // is the first child of 'a', etc.).
        System.out.println(trie.words());

        System.out.println("\n=== Search ===");
        for (String word : new String[]{"cab", "cat", "catch", "ca", "catfishy", "topology"}) {
            System.out.println("search(\"" + word + "\") -> " + trie.search(word));
        }
        // Note: search("ca") is false because "ca" is only a prefix
        // here, not a stored word. The end-of-word flag is the
        // distinction.

        System.out.println("\n=== StartsWith (autocomplete) ===");
        for (String prefix : new String[]{"c", "ca", "cat", "to", "xy"}) {
            System.out.println("startsWith(\"" + prefix + "\") -> " + trie.startsWith(prefix));
        }
        // "xy" diverges immediately -> empty list.

        System.out.println("\n=== Delete ===");
        // Delete a word whose path is shared by longer words.
        // -> Unmark the end-of-word flag on its terminal node, but
        //    DON'T remove any nodes -- the path stays because there
        //    are children below it.
        // "cat" sits at the 't' node under c-a; 't' has children 'f'
        // (catfish) and 'c' (catch), so cleanup is a no-op.
        System.out.println("delete(\"cat\")   -> " + trie.delete("cat"));
        System.out.println("words() -> " + trie.words());
        System.out.println("search(\"cat\")     -> " + trie.search("cat"));
        System.out.println("search(\"catch\")   -> " + trie.search("catch"));
        System.out.println("search(\"catfish\") -> " + trie.search("catfish"));

        // Delete a word whose tail is unique -- cleanup DOES remove
        // nodes. "catch" is c-a-t-c-h. After unmarking 'h', the
        // cleanup walk runs:
        //   * h: not end-of-word, no children -> remove.
        //   * c (under c-a-t): not end-of-word, no children (h was just
        //     removed) -> remove.
        //   * t (under c-a): not end-of-word, but has child 'f' (for
        //     catfish) -> STOP. The catfish path stays intact.
        System.out.println("delete(\"catch\") -> " + trie.delete("catch"));
        System.out.println("words() -> " + trie.words());

        // Try to delete something that isn't there.
        System.out.println("delete(\"xyz\")   -> " + trie.delete("xyz"));

        System.out.println("\n=== Final state ===");
        System.out.println("size=" + trie.size() + ", words=" + trie.words());
    }
}
