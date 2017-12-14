package io.github.chcat.iblt;

import io.github.chcat.iblt.cells.Cells;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Created by ChCat on 12/12/2017.
 */
public abstract class SerializingLookupTable<K,V,S,T> extends InvertibleBloomLookupTableBase<S,T,BiConsumer<K,V>> implements LookupTable<K,V> {
    protected final Function<K,S> keySerializer;
    protected final Function<S,K> keyDeserializer;
    protected final Function<V,T> valueSerializer;
    protected final Function<T,V> valueDeserializer;

    public SerializingLookupTable(Cells<S, T> cells, Function<K, S> keySerializer, Function<S, K> keyDeserializer, Function<V, T> valueSerializer, Function<T, V> valueDeserializer) {
        super(cells);
        this.keySerializer = keySerializer;
        this.keyDeserializer = keyDeserializer;
        this.valueSerializer = valueSerializer;
        this.valueDeserializer = valueDeserializer;
    }

    @Override
    public void put(K key, V value) {
        S serializedKey = keySerializer.apply(key);
        put(serializedKey, valueSerializer.apply(value), getRelatedIndexes(key, serializedKey));
    }

    @Override
    public void remove(K key, V value) {
        S serializedKey = keySerializer.apply(key);
        remove(serializedKey, valueSerializer.apply(value), getRelatedIndexes(key, serializedKey));
    }

    @Override
    public V get(K key) {
        S serializedKey = keySerializer.apply(key);
        T serializedValue = get(serializedKey, getRelatedIndexes(key, serializedKey));
        return valueDeserializer.apply(serializedValue);
    }

    @Override
    protected int[] collect(BiConsumer<K, V> collector, S key, T value) {
        K deserializedKey = keyDeserializer.apply(key);
        collector.accept(deserializedKey, valueDeserializer.apply(value));
        return getRelatedIndexes(deserializedKey, key);
    }

    protected abstract int[] getRelatedIndexes(K key, S serializedKey);
}