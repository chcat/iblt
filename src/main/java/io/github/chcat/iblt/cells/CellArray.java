package io.github.chcat.iblt.cells;

import io.github.chcat.iblt.cells.cell.Cell;

/**
 * Created by ChCat on 12/5/2017.
 */
public class CellArray<V, K> implements Cells<K,V> {

    private final Cell<K,V>[] cells;

    public CellArray(Cell<K,V>[] cells) {
        this.cells = cells;
    }

    @Override
    public int size() {
        return cells.length;
    }

    @Override
    public boolean isViable(int index) {
        return cells[index].isViable();
    }

    @Override
    public boolean isEmpty(int index) {
        return cells[index].isEmpty();
    }

    @Override
    public void put(int index, K key, V value) {
        cells[index].put(key, value);
    }

    @Override
    public void remove(int index, K key, V value) {
        cells[index].remove(key, value);
    }

    @Override
    public K getKey(int index) {
        return cells[index].getKey();
    }

    @Override
    public V getValue(int index) {
        return cells[index].getValue();
    }
}
