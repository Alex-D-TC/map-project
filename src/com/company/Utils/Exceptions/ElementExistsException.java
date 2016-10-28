package com.company.Utils.Exceptions;

/**
 * Created by AlexandruD on 10/23/2016.
 */
public class ElementExistsException extends Exception {

    private static final String MESSAGE = "The element already exists";

    @Override
    public String getMessage() {
        return MESSAGE;
    }

}
