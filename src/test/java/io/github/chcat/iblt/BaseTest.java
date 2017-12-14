package io.github.chcat.iblt;

import org.junit.Test;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;

public class BaseTest {


    private static byte[] toBytes(Long x){
        return String.valueOf(x).getBytes();
    }

    private static Long fromBytes(byte[] x){
        return Long.parseLong(new String(x));
    }


    @Test
    public void test2(){

        long total_time = 0;
        int total_failures = 0;

        for (int test = 1; test <= 210; test++) {

            int opsCount = 1000000;
            int delta = 1000;

            LookupTable<Long, Long> map = LookupTables.create(delta, BaseTest::toBytes, BaseTest::fromBytes, BaseTest::toBytes, BaseTest::fromBytes);

            long[] keys = new long[opsCount];
            long[] values = new long[opsCount];
            int[] indexes = new int[opsCount];

            Random r = new SecureRandom();

            long time = System.currentTimeMillis();

            for (int i = 0; i < opsCount; i++) {
                long k = r.nextLong();
                long v = r.nextLong();

                keys[i] = k;
                values[i] = v;
                indexes[i] = i;

                map.put(k, v);
            }

            time = System.currentTimeMillis() - time;
            if (test > 10) {
                total_time += time;
                System.out.println((double) total_time / (test - 10));
            }

            shuffle(indexes);

            for (int i = 0; i < opsCount - delta; i++) {
                map.remove(keys[indexes[i]], values[indexes[i]]);
            }

            int failed = 0;

            for (int i = opsCount - delta; i < opsCount; i++) {
                try {
                    map.get(keys[indexes[i]]);
                } catch (Exception e) {
                    failed++;
                }

                //if (map.get(keys[indexes[i]])!= values[indexes[i]]) throw new RuntimeException("Corrupted data");
                //System.out.println(indexes[i] + " " + keys[indexes[i]] + " " + values[indexes[i]]);
            }

            if (test > 10) {
                total_failures += failed;
                System.out.println("Failed " + (double)total_failures/(test - 10));

            }

            Map<Long, Long> drain = map.drain();

            for (int i = opsCount - delta; i < opsCount; i++) {
                if (values[indexes[i]] != drain.get(keys[indexes[i]])) throw new RuntimeException("Corrupted data");
            }
        }
    }



    @Test
    public void test(){

        long total_time = 0;
        int total_failures = 0;

        for (int test = 1; test <= 210; test++) {

            int opsCount = 1000000;
            int delta = 1000;

            LookupTable<Long, Long> map = LookupTables.createLongLong(delta);

            long[] keys = new long[opsCount];
            long[] values = new long[opsCount];
            int[] indexes = new int[opsCount];

            Random r = new SecureRandom();

            long time = System.currentTimeMillis();

            for (int i = 0; i < opsCount; i++) {
                long k = r.nextLong();
                long v = r.nextLong();

                keys[i] = k;
                values[i] = v;
                indexes[i] = i;

                map.put(k, v);
            }

            time = System.currentTimeMillis() - time;
            if (test > 10) {
                total_time += time;
                System.out.println((double) total_time / (test - 10));
            }

            shuffle(indexes);

            for (int i = 0; i < opsCount - delta; i++) {
                map.remove(keys[indexes[i]], values[indexes[i]]);
            }

            int failed = 0;

            for (int i = opsCount - delta; i < opsCount; i++) {
                try {
                    map.get(keys[indexes[i]]);
                } catch (Exception e) {
                    failed++;
                }

                //if (map.get(keys[indexes[i]])!= values[indexes[i]]) throw new RuntimeException("Corrupted data");
                //System.out.println(indexes[i] + " " + keys[indexes[i]] + " " + values[indexes[i]]);
            }

            if (test > 10) {
                total_failures += failed;
                System.out.println("Failed " + (double)total_failures/(test - 10));

            }

            Map<Long, Long> drain = map.drain();

            for (int i = opsCount - delta; i < opsCount; i++) {
                if (values[indexes[i]] != drain.get(keys[indexes[i]])) throw new RuntimeException("Corrupted data");
            }
        }
    }

    public static void shuffle(int[] data){
        Random r = new Random();

        int l = data.length;
        for (int i = 0; i < l; i++){
            int j = i + r.nextInt(l-i);
            int t = data[i];
            data[i] = data[j];
            data[j] = t;
        }
    }
}
