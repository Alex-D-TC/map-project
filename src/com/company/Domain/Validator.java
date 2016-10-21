package com.company.Domain;

/**
 * Created by AlexandruD on 10/14/2016.
 */
public abstract class Validator<T> {

    public abstract boolean validate(T obj);

}
