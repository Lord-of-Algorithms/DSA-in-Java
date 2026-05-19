package trie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A trie (pronounced "try"): a tree-shaped data structure where each
 * node holds a single character, and a stored word corresponds to a
 * path from the root to a node flagged as end-of-word.
 *
 * <p><b>Why a trie?</b> Two operations a trie does extremely well that
 * a {@code HashSet} or balanced BST cannot:
 * <ul>
 *   <li><b>Prefix lookup</b> — "give me every word that starts with
 *       {@code "ca"}" runs in {@code O(L + K)} where {@code L} is the
 *       prefix length and {@code K} is the number of matches. A
 *       {@code HashSet} would have to scan every entry.</li>
 *   <li><b>Ordered enumeration</b> — depth-first traversal returns
 *       words in a deterministic order determined by each node's
 *       children-map. This implementation uses {@link java.util.LinkedHashMap}
 *       (insertion order); swap to {@link java.util.TreeMap} in
 *       {@link TrieNode} and you get alphabetical order for free, no
 *       separate sort needed.</li>
 * </ul>
 *
 * <p><b>Complexity</b>
 * <pre>
 *   L = length of the word / prefix
 *   N = total words stored
 *   K = matches under a given prefix
 *
 *   insert       O(L)
 *   delete       O(L)
 *   search       O(L)
 *   startsWith   O(L + K)
 *   space        O(N * L) worst case; much less when prefixes overlap
 * </pre>
 *
 * <p>Used in: autocomplete keyboards, spell-check and "did-you-mean?",
 * T9 / predictive text, IP-routing longest-prefix match, DNS lookups.
 */
public class Trie {

    /**
     * The root sentinel — holds no character; every word path begins
     * at the root and takes its first character from one of the root's
     * children.
     */
    public final TrieNode root = new TrieNode('\0', null);

    private int wordCount;

    /**
     * {@code true} iff no words are stored.
     */
    public boolean isEmpty() {
        return wordCount == 0;
    }

    /**
     * Total number of stored words.
     */
    public int size() {
        return wordCount;
    }

    /**
     * Remove every word.
     */
    public void clear() {
        root.children.clear();
        wordCount = 0;
    }

    /**
     * Insert {@code word} into the trie. No-op if the word is already
     * present. Null or empty words are ignored.
     */
    public void insert(String word) {
        if (word == null || word.isEmpty()) return;
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            // If the child for this character is missing, create it.
            // Shared prefixes share nodes — this is the heart of the
            // trie's memory advantage.
            TrieNode child = node.children.get(c);
            if (child == null) {
                child = new TrieNode(c, node);
                node.children.put(c, child);
            }
            node = child;
        }
        // Mark the final node as end-of-word. Only bump wordCount if
        // we transitioned the flag from false to true — re-inserting an
        // existing word is a no-op.
        if (!node.isEndOfWord) {
            node.isEndOfWord = true;
            wordCount++;
        }
    }

    /**
     * Returns {@code true} if {@code word} is stored in the trie.
     * Note: "stored" means the path exists AND the final node is
     * flagged end-of-word. {@code search("cat")} returns {@code false}
     * if only {@code "catch"} is stored, even though the {@code "cat"}
     * path is reachable as a prefix.
     */
    public boolean search(String word) {
        if (word == null || word.isEmpty()) return false;
        TrieNode node = findTerminalNode(word);
        return node != null && node.isEndOfWord;
    }

    /**
     * Delete {@code word} from the trie and remove any nodes that became
     * orphaned (no longer end-of-word and no remaining children).
     * Returns {@code true} if a word was actually removed, {@code false}
     * if {@code word} was never in the trie.
     *
     * <p>The <b>cleanup phase</b> runs after the end-of-word flag is
     * cleared: walk back up from the deleted leaf and remove every node
     * that has <em>no end-of-word flag AND no children</em>. Stop at the
     * first node that's still part of another word's path — either it
     * carries its own end-of-word flag, or it has another child
     * branching off. Deleting {@code "cab"} from a trie that also stores
     * {@code "cat"} removes only the {@code 'b'} node; the shared
     * {@code "c-a"} path stays because {@code 'a'} still has its
     * {@code 't'} child.
     */
    public boolean delete(String word) {
        if (word == null || word.isEmpty()) return false;
        TrieNode node = findTerminalNode(word);
        if (node == null || !node.isEndOfWord) return false;

        node.isEndOfWord = false;
        wordCount--;

        // Cleanup walk: remove the deleted leaf and its empty ancestors
        // until we hit a node that's still useful.
        while (!node.isRoot() && !node.isEndOfWord && node.children.isEmpty()) {
            TrieNode parent = node.parent;
            parent.children.remove(node.character);
            node = parent;
        }
        return true;
    }

    /**
     * Returns every word stored in the trie that begins with
     * {@code prefix}. Returns an empty list if the prefix can't be
     * walked (a non-existent prefix means no matches by definition).
     *
     * <p>An empty or {@code null} prefix matches every word.
     *
     * <p>Algorithm: descend along the prefix to find its terminal node,
     * then DFS the subtree rooted there to collect every end-of-word
     * descendant. Each descendant uniquely identifies one stored word —
     * the word string is the path from root to that descendant.
     */
    public List<String> startsWith(String prefix) {
        List<String> matches = new ArrayList<>();
        TrieNode prefixNode = (prefix == null || prefix.isEmpty())
                ? root
                : findTerminalNode(prefix);
        if (prefixNode == null) return matches;

        StringBuilder path = new StringBuilder(prefix == null ? "" : prefix);
        collectWords(prefixNode, path, matches);
        return matches;
    }

    /**
     * Returns every word stored in the trie, in insertion-preserving
     * order of the underlying {@link java.util.LinkedHashMap}s. Useful for tests
     * and debugging.
     */
    public List<String> words() {
        return startsWith("");
    }

    /**
     * Walks the trie following {@code path} character-by-character and
     * returns the terminal node, or {@code null} if any character has
     * no corresponding child along the way.
     */
    private TrieNode findTerminalNode(String path) {
        TrieNode node = root;
        for (int i = 0; i < path.length(); i++) {
            char c = path.charAt(i);
            TrieNode child = node.children.get(c);
            if (child == null) return null;
            node = child;
        }
        return node;
    }

    /**
     * DFS helper for {@link #startsWith(String)} and {@link #words()}.
     * Appends {@code node.character} to {@code path} on the way down,
     * collects {@code path.toString()} into {@code out} whenever the
     * current node is end-of-word, recurses into every child, and
     * removes the appended character on the way back up to leave
     * {@code path} untouched for the caller's next branch.
     */
    private void collectWords(TrieNode node, StringBuilder path, List<String> out) {
        if (node.isEndOfWord) {
            out.add(path.toString());
        }
        for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
            path.append(entry.getKey());
            collectWords(entry.getValue(), path, out);
            path.deleteCharAt(path.length() - 1);
        }
    }
}
