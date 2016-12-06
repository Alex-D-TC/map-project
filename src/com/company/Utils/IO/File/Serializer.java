package com.company.Utils.IO.File;

/**
 * Created by AlexandruD on 10/27/2016.
 */
public interface Serializer<T> {

    /**
     * Produces the string representation of an object. Used for custom serialization
     * @param elem - The object to file
     * @return - The string representation
     */
    String serialize(T elem);

}
