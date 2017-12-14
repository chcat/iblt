package io.github.chcat.iblt.cells.cell;

import static io.github.chcat.iblt.utils.Bytes.decode;
import static io.github.chcat.iblt.utils.Bytes.encodeAndAdd;

/**
 * Created by ChCat on 11/21/2017.
 */
public final class BytesBytes extends CellBase<byte[], byte[]> {

    private static final int DEFAULT_ITEM_SIZE = 16;

    private byte[] keys = new byte[DEFAULT_ITEM_SIZE];
    private byte[] values = new byte[DEFAULT_ITEM_SIZE];

    @Override
    public void accumulate(byte[] key, byte[] value) {
        keys = encodeAndAdd(keys, key);
        values = encodeAndAdd(values, value);
    }

    @Override
    public void dissipate(byte[] key, byte[] value) {
        keys = encodeAndAdd(keys, key);
        values = encodeAndAdd(values, value);
    }

    @Override
    public byte[] getKey() {
        return decode(keys);
    }

    @Override
    public byte[] getValue() {
        return decode(values);
    }
}
