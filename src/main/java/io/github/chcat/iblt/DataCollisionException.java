package io.github.chcat.iblt;

/**
 * Created by ChCat on 1/29/2018.
 */
public class DataCollisionException extends IllegalStateException {

    public DataCollisionException(){
        super("The operation cannot be completed because of colliding entries. That can be caused either by the table being loaded too much or by the bad choice of hashing functions");
    }

}
