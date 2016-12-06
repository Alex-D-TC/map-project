package com.company.Utils.IO.File;

/**
 * Created by AlexandruD on 10/14/2016.
 */
public interface Parser<T> {

    /**
     * Parses a line to deserialize an element
     * @param line - The line to parse
     * @return The element if parsing succeeds, null otherwise
     */
    T parse(String line);
}
