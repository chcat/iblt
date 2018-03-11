package io.github.chcat.iblt;

/**
 * Created by ChCat on 1/29/2018.
 */
public class DataCorruptionException extends IllegalStateException {

    public DataCorruptionException(){
        super("Detected corruption of the data");
    }

}
