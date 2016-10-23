package com.company.Utils.Exceptions;

/**
 * Created by AlexandruD on 10/23/2016.
 */
public class FailedTestException extends Exception {

    private static final String MESSAGE = "One or more tests has failed... Consult error log... Bitch";

    @Override
    public String getMessage() {
        return MESSAGE;
    }

}
