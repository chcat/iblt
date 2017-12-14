package io.github.chcat.iblt.cells.cell;

/**
 * Created by ChCat on 11/28/2017.
 */
public abstract class CellBase<K,V> implements Cell<K,V> {

    private int count;

    @Override
    public final boolean isViable() {
        return count == 1;
    }

    @Override
    public final boolean isEmpty() {
        return count == 0;
    }


    @Override
    public final void put(K key, V value) {
        count++;
        accumulate(key,value);
    }

    @Override
    public final void remove(K key, V value) {
        count--;
        dissipate(key,value);
    }

    protected abstract void accumulate(K key, V value);

    protected abstract void dissipate(K key, V value);
}
