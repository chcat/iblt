package io.github.chcat.iblt.utils;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Murmur3 is successor to Murmur2 fast non-crytographic hash algorithms.
 *
 * This is a public domain code with no copyrights.
 *
 * From homepage of MurmurHash: "All MurmurHash versions are public domain software,
 * and the author disclaims all copyright to their code."
 */
public class Murmur3 {

    public static final int DEFAULT_SEED = 104729;
    private static final int C1 = 0xcc9e2d51;
    private static final int C2 = 0x1b873593;
    private static final int R1 = 15;
    private static final int R2 = 13;
    private static final int M = 5;
    private static final int N = 0xe6546b64;

    public static Function<byte[], int[]> multiHashForBytes(int n){
        return multiHash((bytes, seed) -> hash(bytes, seed), n);
    }

    public static Function<Long, int[]> multiHashForLong(int n){
        return multiHash((bytes, seed) -> hash(bytes, seed), n);
    }

    public static Function<Integer, int[]> multiHashForInt(int n){
        return multiHash((bytes, seed) -> hash(bytes, seed), n);
    }

    private static <K> Function<K, int[]> multiHash(BiFunction<K, Integer,Integer> hash, int n){
        return k -> {
            int[] multiHash = new int[n];
            for (int i = 0; i < n; i++){
                multiHash[i] = hash.apply(k, DEFAULT_SEED + i);
            }
            return multiHash;
        };
    }

    public static int hash(int data, int seed){
        int hash = seed;

        data *= C1;
        data = Integer.rotateLeft(data, R1);
        data *= C2;
        hash ^= data;
        hash = Integer.rotateLeft(hash, R2) * M + N;

        hash ^= 4;
        hash ^= (hash >>> 16);
        hash *= 0x85ebca6b;
        hash ^= (hash >>> 13);
        hash *= 0xc2b2ae35;
        hash ^= (hash >>> 16);

        return hash;
    }

    public static int hash(long data, int seed){

        int hash = seed;

        int k = (int)data;
        k *= C1;
        k = Integer.rotateLeft(k, R1);
        k *= C2;
        hash ^= k;
        hash = Integer.rotateLeft(hash, R2) * M + N;

        k = (int)(data >>> Integer.BYTES * 8);
        k *= C1;
        k = Integer.rotateLeft(k, R1);
        k *= C2;
        hash ^= k;
        hash = Integer.rotateLeft(hash, R2) * M + N;

        hash ^= 8;
        hash ^= (hash >>> 16);
        hash *= 0x85ebca6b;
        hash ^= (hash >>> 13);
        hash *= 0xc2b2ae35;
        hash ^= (hash >>> 16);

        return hash;
    }

    public static int hash(byte[] data, int seed){
        int length = data.length;
        int hash = seed;
        final int nblocks = length >> 2;

        for (int i = 0; i < nblocks; i++) {
            int i_4 = i << 2;
            int k = (data[i_4] & 0xff)
                    | ((data[i_4 + 1] & 0xff) << 8)
                    | ((data[i_4 + 2] & 0xff) << 16)
                    | ((data[i_4 + 3] & 0xff) << 24);

            k *= C1;
            k = Integer.rotateLeft(k, R1);
            k *= C2;
            hash ^= k;
            hash = Integer.rotateLeft(hash, R2) * M + N;
        }

        int idx = nblocks << 2;
        int k1 = 0;
        switch (length - idx) {
            case 3:
                k1 ^= data[idx + 2] << 16;
            case 2:
                k1 ^= data[idx + 1] << 8;
            case 1:
                k1 ^= data[idx];

                k1 *= C1;
                k1 = Integer.rotateLeft(k1, R1);
                k1 *= C2;
                hash ^= k1;
        }

        hash ^= length;
        hash ^= (hash >>> 16);
        hash *= 0x85ebca6b;
        hash ^= (hash >>> 13);
        hash *= 0xc2b2ae35;
        hash ^= (hash >>> 16);

        return hash;
    }

}
