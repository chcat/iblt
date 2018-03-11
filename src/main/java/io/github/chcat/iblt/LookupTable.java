package io.github.chcat.iblt;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

/**
 * Created by ChCat on 11/21/2017.
 */
public interface LookupTable<K,V> {

    void put(K key, V value);

    boolean remove(K key, V value) throws IllegalStateException;

    Optional<V> get(K key) throws IllegalStateException;

    void drain(BiConsumer<K,V> consumer) throws IllegalStateException;

    default Map<K, V> drain() throws IllegalStateException {
        Map<K,V> map = new HashMap<>();
        drain((k,v) -> map.put(k,v)); // TODO: check for duplicates
        return map;

    }
}
