package io.github.chcat.iblt;

import io.github.chcat.iblt.cells.Cells;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

/**
 * Created by ChCat on 12/7/2017.
 */
public abstract class InvertibleBloomLookupTableBase<K,V,C>  {
    protected final int size;
    protected final boolean failFast = true;
    protected final Cells<K,V> cells;

    protected InvertibleBloomLookupTableBase(Cells<K,V> cells){
        this.size = cells.size();
        this.cells = cells;
    }

    protected final void put(K key, V value, int[] indexes){
        for (int index : normalize(indexes)){
            cells.put(index, key, value);
        }
    }

    protected final boolean remove(K key, V value, int[] indexes) throws IllegalStateException {
        indexes = normalize(indexes);
        boolean allConsistent = true;

        for (int index : normalize(indexes)){
            cells.remove(index, key, value);
            allConsistent &= !cells.isInconsistent(index);
        }

        // The root cause of the detected inconsistency may be an action in the distant past
        // so it can not be repaired rolling back the current modification...
        if (failFast && !allConsistent){
            throw new DataCorruptionException();
        }

        return allConsistent;
    }

    protected final Optional<V> get(K key, int[] indexes) throws IllegalStateException {
        for (int index : normalize(indexes)){
            if (cells.isEmpty(index)){
                // no value
                return Optional.empty();
            } else if (cells.isViable(index)){
                V value = cells.getValue(index);
                // check the key matches
                cells.remove(index, key, value);

                if (cells.isEmpty(index)){
                    cells.put(index, key, value);
                    return Optional.ofNullable(value);
                } else {
                    cells.put(index, key, value);
                    return Optional.empty();
                }
            }
        }

        if (failFast){
            throw new IllegalStateException(String.format("Not possible to get the value for %s", key));
        }

        return null;
    }

    public void drain(C collector) {
        Queue<Integer> viableCells = new ArrayDeque<>(size);
        int emptyCount = 0;

        for (int index = 0; index < size; index++){
            if (cells.isEmpty(index)){
                emptyCount++;
            } else if (cells.isViable(index)){
                viableCells.add(index);
            }
        }

        for (;;) {
            if (viableCells.isEmpty()){
                break;
            }

            int index = viableCells.poll();

            if (cells.isViable(index)){
                K key = cells.getKey(index);
                V value = cells.getValue(index);
                int[] relatives = collect(collector, key, value);

                for (int related : normalize(relatives)){
                    cells.remove(related, key, value);

                    if (cells.isEmpty(related)){
                        emptyCount++;
                    } else if (cells.isViable(related)){
                        viableCells.add(related);
                    }
                }
            }
        }

        if (emptyCount != size){
            throw new RuntimeException();
        }
    }

    protected abstract int[] collect(C collector, K key, V value);

    private final int[] normalize(int[] indexes){
        for (int i = 0; i < indexes.length; i++){
            int index = indexes[i];
            // There are multiple ways to get a pseudo random positive number within [0..size)
            // using the value of the hash as a seed.
            // Dropping the sign bit and subsequent division by modulo occurs
            // to be the fastest on the test machine.
            index = index & (-1 >>> 1); // drops the sign bit
            index %= size;
            indexes[i] = index;
        }
        // Note we neither force index uniqueness here nor collapse the duplicates:
        // simulations shows the impact on success rate to be quite small.
        return indexes;
    }
}
