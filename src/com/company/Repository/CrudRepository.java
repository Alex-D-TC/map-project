package com.company.Repository;

import com.company.Utils.Exceptions.ElementExistsException;
import com.company.Utils.Exceptions.ElementNotFoundException;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by AlexandruD on 10/8/2016.
 */
public abstract class CrudRepository<T> {

    /**
     * Adds a post
     * @param elem - The post to add
     */
    public abstract void add(T elem) throws ElementExistsException;

    /**
     * Gets all items
     * @return - A list of all posts
     */
    public abstract Iterable<T> getAll();

    /**
     * Gets all items matching the given unary operator
     * @param op - The unary operator used to match items
     * @return - A list of all matched items
     */
    public abstract Iterable<T> get(Predicate<T> op);

    /**
     * Removes a item
     * @param elem - The item to remove
     * @return - The removed item. Null if item not found
     */
    public abstract T remove(T elem) throws ElementNotFoundException;

    /**
     * Updates an item
     * @param original - The old item
     * @param newElem - The replacement item
     */
    public abstract void update(T original, T newElem) throws ElementNotFoundException;
}


