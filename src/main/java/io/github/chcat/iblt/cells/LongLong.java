package io.github.chcat.iblt.cells;

/**
 * Created by ChCat on 12/5/2017.
 */
public class LongLong implements Cells<Long,Long> {
    private static final int COUNT = 0;
    private static final int KEY = 1;
    private static final int VALUE = 2;

    private final int size;
    private final long[] data;

    public LongLong(int size) {
        this.size = size;
        this.data = new long[size * 3];
    }

    @Override
    public int size() {
        return size;
    }

    private static int address(int type, int index){
        return index * 3 + type;
    }

    @Override
    public boolean isViable(int index) {
        return data[address(COUNT, index)] == 1;
    }

    @Override
    public boolean isEmpty(int index) {
        return data[address(COUNT, index)] == 0;
    }

    @Override
    public void put(int index, Long key, Long value) {
        data[address(COUNT, index)]++;
        data[address(KEY, index)] ^= key;
        data[address(VALUE, index)] ^= value;
    }

    @Override
    public void remove(int index, Long key, Long value) {
        data[address(COUNT, index)]--;
        data[address(KEY, index)] ^= key;
        data[address(VALUE, index)] ^= value;
    }

    @Override
    public Long getKey(int index) {
        return data[address(KEY, index)];
    }

    @Override
    public Long getValue(int index) {
        return  data[address(VALUE, index)];
    }
}
