package com.company.Utils.Exceptions;

/**
 * Created by AlexandruD on 10/23/2016.
 */
public class ElementNotFoundException extends Exception {

    private final String MESSAGE;

    public ElementNotFoundException() {
        MESSAGE = "The element does not exist";
    }

    public ElementNotFoundException(String message) {
        MESSAGE = message;
    }

    @Override
    public String getMessage() {
        return MESSAGE;
    }


}
