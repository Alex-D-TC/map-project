package com.company.Utils.Exceptions;

/**
 * Created by AlexandruD on 10/23/2016.
 */
public class ElementNotFoundException extends Exception {

    private static final String MESSAGE = "The element does not exist";

    @Override
    public String getMessage() {
        return MESSAGE;
    }


}
