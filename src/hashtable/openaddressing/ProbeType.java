package hashtable.openaddressing;

/**
 * Enumerates the supported probe sequences for open addressing.
 */
public enum ProbeType {
    Linear,     // offset(i) = i        -> home, home+1, home+2, ...
    Quadratic   // offset(i) = i * i    -> home, home+1, home+4, home+9, ...
}
