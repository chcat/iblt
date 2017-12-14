package io.github.chcat.iblt;

import io.github.chcat.iblt.cells.Cells;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Created by ChCat on 11/14/2017.
 */
public class RawLookupTable<K,V> extends InvertibleBloomLookupTableBase<K,V, BiConsumer<K,V>> implements LookupTable<K,V> {

    private final Function<K, int[]> multiHash;

    public RawLookupTable(Cells<K,V> cells, Function<K, int[]> multiHash){
        super(cells);
        this.multiHash = multiHash;
    }

    public void put(K key, V value){
        put(key, value, getRelatedIndexes(key));
    }

    public void remove(K key, V value){
        remove(key, value, getRelatedIndexes(key));
    }

    public V get(K key){
        return get(key, getRelatedIndexes(key));
    }

    @Override
    protected int[] collect(BiConsumer<K,V> collector, K key, V value) {
        collector.accept(key, value);
        return getRelatedIndexes(key);
    }

    protected int[] getRelatedIndexes(K key){
        return multiHash.apply(key);
    }
}
