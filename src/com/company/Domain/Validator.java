package com.company.Domain;

/**
 * Created by AlexandruD on 10/14/2016.
 */
public abstract class Validator<T> {

    /**
     * Validates an object
     * @param obj The object to validate
     * @return true if the object is valid, false otherwise
     */
    public abstract boolean validate(T obj);

}
