package io.github.chcat.iblt;

import io.github.chcat.iblt.cells.Cells;
import io.github.chcat.iblt.cells.cell.Cell;
import io.github.chcat.iblt.cells.CellArray;
import io.github.chcat.iblt.cells.LongLong;
import io.github.chcat.iblt.cells.cell.BytesBytes;

import java.util.function.Function;
import java.util.function.Supplier;

import static io.github.chcat.iblt.utils.Murmur3.multiHashForBytes;
import static io.github.chcat.iblt.utils.Murmur3.multiHashForLong;


/**
 * Created by ChCat on 11/27/2017.
 */
public class LookupTables {

//    public static LookupTable<Integer,Integer> createIntInt(int capacity){
//        return create(capacity, IntInt::new);
//    }
//
    public static LookupTable<Long,Long> createLongLong(int capacity){
        Cells<Long,Long> cells = new LongLong(capacity * 10);
        return new RawLookupTable<>(cells, multiHashForLong(5));
    }

    public static <K,V> LookupTable<K,V> create(int capacity, Function<K, byte[]> keySerializer, Function<byte[], K> keyDeserializer, Function<V, byte[]> valueSerializer, Function<byte[], V> valueDeserializer){
        int size = capacity * 10;

        Cell<byte[],byte[]>[] cells = new Cell[size];
        for (int index = 0; index < size; index++){
            cells[index] = new BytesBytes();
        }

        return new PreSerializingLookupTable<>(new CellArray<>(cells), multiHashForBytes(5), keySerializer, keyDeserializer, valueSerializer, valueDeserializer);
    }

    public static <K,V> LookupTable<K,V> create(int capacity, Supplier<Cell<K,V>> cellFactory){
        //return new RawLookupTable<>(capacity * 10, multiHash(Murmur3::hash, 5).compose(Object::hashCode), cellFactory);
        return null;
    }

    private LookupTables(){}

}
