package io.github.chcat.iblt.utils;

import java.util.Arrays;

/**
 * Created by ChCat on 11/28/2017.
 */
public class Bytes {

    public static boolean checkAllZeros(byte[] data){
        int accumulator = 0;
        for (int i = 0; i < data.length; i++){
            accumulator |= data[i];
        }

        return (byte) accumulator == 0;
    }

    public static byte[] encodeAndAdd(byte[] sum, byte[] addend){
        int length = addend.length;
        int position = 0;

        int temp = length;
        while ((temp & (-1 << 7)) != 0){
            sum[position] ^= (byte)(temp | 0x80);
            temp >>>= 7;
            position++;
        }
        sum[position] ^= (byte)temp;
        position++;

        byte[] out = ensureCapacity(sum, position + length);

        for (int j = 0; j < length;){
            out[position++] ^= addend[j++];
        }

        return out;
    }

    public static byte[] decode(byte[] encoded){
        int position = 0;
        int limit = encoded.length;
        int length = 0;

        for (int i = 0; ; i += 7){
            if (position >= limit || i > 28) {
                throw new IllegalArgumentException();
            }

            int b = encoded[position++];
            length |= (b & 0x7F) << i;

            if ((b & 0x80) == 0) {
                break;
            }
        }

        return Arrays.copyOfRange(encoded,position,position+length);

    }

    private static byte[] ensureCapacity(byte[] x, int capacity){
        int currentCapacity = x.length;
        if (currentCapacity >= capacity){
            return x;
        } else {
            int newCapacity = currentCapacity * 2;
            return Arrays.copyOf(x, newCapacity >= capacity ? newCapacity : capacity);
        }
    }

}
