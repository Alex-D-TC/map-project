package com.company.Tests;


import com.company.Tests.Repository.FileRepositoryTest;
import com.company.Tests.Repository.InMemoryRepositoryTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by AlexandruD on 10/8/2016.
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
        InMemoryRepositoryTest.class,
        FileRepositoryTest.class
})
public class Tests {

}
