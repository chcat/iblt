package io.github.chcat.iblt;

import io.github.chcat.iblt.cells.Cells;

import java.util.function.Function;

/**
 * Created by ChCat on 12/12/2017.
 */
public class PostSerializingLookupTable<K,V,S,T> extends SerializingLookupTable<K,V,S,T> {
    protected final Function<K, int[]> multiHash;

    public PostSerializingLookupTable(Cells<S, T> cells, Function<K, int[]> multiHash, Function<K, S> keySerializer, Function<S, K> keyDeserializer, Function<V, T> valueSerializer, Function<T, V> valueDeserializer) {
        super(cells, keySerializer, keyDeserializer, valueSerializer, valueDeserializer);
        this.multiHash = multiHash;
    }

    @Override
    protected int[] getRelatedIndexes(K key, S serializedKey) {
        return multiHash.apply(key);
    }
}
