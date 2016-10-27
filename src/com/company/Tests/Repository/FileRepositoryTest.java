package com.company.Tests.Repository;

import com.company.Repository.FileRepository;
import org.junit.After;
import org.junit.Before;

import java.io.*;

/**
 * Created by AlexandruD on 10/23/2016.
 */
public class FileRepositoryTest extends CrudRepositoryTest {

    private static final String FILE_PATH = "./testData";

    @Before
    public void setUp() {

        repo = new FileRepository<>(FILE_PATH, (line) -> (line), (elem) -> (elem));

    }

    @After
    public void tearDown() throws IOException {
        // clear test data
        File file = new File(FILE_PATH);
        Writer writer = new BufferedWriter(new FileWriter(file));
        writer.write("");
    }

}
