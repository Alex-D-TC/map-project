package com.company.Tests.Repository;

import com.company.Repository.InMemoryRepository;
import org.junit.Before;

/**
 * Created by AlexandruD on 10/23/2016.
 */
public class InMemoryRepositoryTest extends CrudRepositoryTest {

    @Before
    public void setUp() {
        repo = new InMemoryRepository<>();
    }

}
