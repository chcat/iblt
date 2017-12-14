package io.github.chcat.iblt.cells.cell;

/**
 * Created by ChCat on 11/16/2017.
 */
public interface Cell<K,V>{
    boolean isViable();
    boolean isEmpty();

    void put(K key, V value);
    void remove(K key, V value);

    K getKey();
    V getValue();
}
