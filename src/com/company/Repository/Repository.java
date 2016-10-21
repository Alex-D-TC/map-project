package com.company.Repository;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by AlexandruD on 10/8/2016.
 */
public interface Repository<T> {

    /**
     * Adds a post
     * @param elem - The post to add
     */
    void add(T elem);

    /**
     * Gets all items
     * @return - A list of all posts
     */
    List<T> getAll();

    /**
     * Gets all items matching the given unary operator
     * @param op - The unary operator used to match items
     * @return - A list of all matched items
     */
    List<T> get(Predicate<T> op);

    /**
     * Removes a item
     * @param elem - The item to remove
     * @return - The removed item. Null if item not found
     */
    T remove(T elem);

    /**
     * Updates an item
     * @param original - The old item
     * @param newElem - The replacement item
     */
    void update(T original, T newElem);
}


