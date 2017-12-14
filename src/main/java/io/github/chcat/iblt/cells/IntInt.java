package io.github.chcat.iblt.cells;

/**
 * Created by ChCat on 12/5/2017.
 */
public class IntInt implements Cells<Integer, Integer> {
    private static final int COUNT = 0;
    private static final int KEY = 1;
    private static final int VALUE = 2;

    private final int size;
    private final int[] data;

    public IntInt(int size) {
        this.size = size;
        this.data = new int[size * 3];
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
    public void put(int index, Integer key, Integer value) {
        data[address(COUNT, index)]++;
        data[address(KEY, index)] ^= key;
        data[address(VALUE, index)] ^= value;
    }

    @Override
    public void remove(int index, Integer key, Integer value) {
        data[address(COUNT, index)]--;
        data[address(KEY, index)] ^= key;
        data[address(VALUE, index)] ^= value;
    }

    @Override
    public Integer getKey(int index) {
        return data[address(KEY, index)];
    }

    @Override
    public Integer getValue(int index) {
        return  data[address(VALUE, index)];
    }
}
