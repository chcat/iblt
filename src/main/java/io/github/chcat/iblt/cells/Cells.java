package io.github.chcat.iblt.cells;

/**
 * This interface represents a set of cells accessible by indexes.
 *
 * Created by ChCat on 12/2/2017.
 */
public interface Cells<K,V> {
    /**
     * @return the number of cells accessible through this object
     */
    int size();

    /**
     * Checks viability of the cell addressed by index. A cell is considered viable if a key-value pair can be
     * extracted from it.
     * @param index address of the cell to check, between {@code 0} and {@code size()-1} both inclusive
     * @return true if the cell is viable
     */
    boolean isViable(int index);

    /**
     * Checks emptiness of the cell addressed by index.
     * @param index address of the cell to check, between {@code 0} and {@code size()-1} both inclusive
     * @return true if the cell is empty
     */
    boolean isEmpty(int index);

    default boolean isInconsistent(int index){
        return false;
    };

    /**
     * Accumulates the key-value pair in the cell addressed by index.
     * @param index address of the cell
     * @param key the key to accumulate
     * @param value the value to accumulate
     */
    void put(int index, K key, V value);

    /**
     * Dissipates the key-value pair from the cell addressed by index.
     * @param index address of the cell
     * @param key the key to accumulate
     * @param value the value to accumulate
     */
    void remove(int index, K key, V value);

    /**
     * Returns a key being held by the cell addressed by {@code index}. This method may be called only on a viable cell.
     * @param index address of the cell to inspect
     * @return key held by the cell, if the cell is viable, otherwise not specified
     */
    K getKey(int index);
    /**
     * Returns a value being held by the cell addressed by {@code index}. This method may be called only on a viable cell.
     * @param index address of the cell to inspect
     * @return value held by the cell, if the cell is viable, otherwise not specified
     */
    V getValue(int index);
}
