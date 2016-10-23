package com.company.Tests;

import com.company.Utils.Exceptions.FailedTestException;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Created by AlexandruD on 10/23/2016.
 */
public class TestRunner {

    public void run() throws FailedTestException {

        Result result = JUnitCore.runClasses(Tests.class);

        for(Failure fail : result.getFailures()) {
            System.err.println(fail.getMessage());
        }

        if(result.getFailureCount() > 0) {
            throw new FailedTestException();
        }
    }

}
