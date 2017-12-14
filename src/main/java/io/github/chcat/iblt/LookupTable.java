package io.github.chcat.iblt;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Created by ChCat on 11/21/2017.
 */
public interface LookupTable<K,V> {

    void put(K key, V value);

    void remove(K key, V value);

    V get(K key);

    void drain(BiConsumer<K,V> consumer);

    default Map<K, V> drain() {
        Map<K,V> map = new HashMap<>();
        drain((k,v) -> map.put(k,v)); // TODO: check for duplicates
        return map;

    }
}
