package trie;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * One node of a {@link Trie}. Each node holds a single character and a
 * map of its children (one child per outgoing character). A node also
 * carries a flag indicating whether the path from the root to this node
 * spells a stored word — without that flag the trie could not tell
 * {@code "cat"} (a stored word) from {@code "cat"} appearing only as a
 * prefix of {@code "catch"}.
 *
 * <p>The root node is created with the sentinel character {@code '\0'}
 * and is never a stored word itself. Every other node holds the
 * character that its incoming edge corresponds to.
 *
 * <p><b>Why {@link LinkedHashMap} for {@link #children}?</b> The map
 * choice decides the iteration order of a node's children, which in
 * turn decides the order of words coming out of
 * {@link Trie#words()} and {@link Trie#startsWith(String)}.
 * <ul>
 *   <li>{@code LinkedHashMap} (used here) — iterates in <b>insertion
 *       order</b>. Inserting {@code "city"} before {@code "cio"} makes
 *       {@code 't'} come before {@code 'o'} in the children of {@code 'i'},
 *       so {@link Trie#words()} returns {@code ["city", "cio"]} — NOT
 *       alphabetical, just stable.</li>
 *   <li>{@code HashMap} — iteration order is unspecified and may change
 *       between runs. Slightly less per-entry overhead. Use this when
 *       result order doesn't matter to the caller.</li>
 *   <li>{@code TreeMap} — iterates in <b>natural (alphabetical) order</b>
 *       of the keys. {@link Trie#words()} would then return
 *       {@code ["cio", "city"]} regardless of insertion order. Cost:
 *       child lookup becomes {@code O(log k)} instead of {@code O(1)},
 *       where {@code k} is the number of children at a node (small in
 *       practice — ≤ 26 for English).</li>
 * </ul>
 */
public class TrieNode {

    /**
     * The character this node holds. {@code '\0'} for the root sentinel.
     */
    public final char character;

    /**
     * Parent in the trie. {@code null} for the root.
     */
    public final TrieNode parent;

    /**
     * {@code true} iff the path from the root to this node spells a stored word.
     */
    public boolean isEndOfWord;

    /**
     * Outgoing edges, keyed by the child's character.
     */
    public final Map<Character, TrieNode> children = new LinkedHashMap<>();

    public TrieNode(char character, TrieNode parent) {
        this.character = character;
        this.parent = parent;
    }

    /**
     * {@code true} iff this is the root sentinel (no parent, no character).
     */
    public boolean isRoot() {
        return parent == null;
    }
}
