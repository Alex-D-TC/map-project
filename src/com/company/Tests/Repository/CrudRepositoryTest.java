package com.company.Tests.Repository;

import com.company.Repository.CrudRepository;
import com.company.Utils.Exceptions.ElementExistsException;
import com.company.Utils.Exceptions.ElementNotFoundException;
import org.junit.Assert;
import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by AlexandruD on 10/23/2016.
 */
public class CrudRepositoryTest {

    protected CrudRepository<String> repo;


    @Test(expected = Test.None.class)
    public void testAdd() throws ElementExistsException {
        repo.add("Harambe");
    }

    @Test(expected = ElementExistsException.class)
    public void testAddExisting() throws ElementExistsException {
        repo.add("Harambe");
        repo.add("Harambe");
    }

    @Test(expected = Test.None.class)
    public void testRemove() throws ElementExistsException, ElementNotFoundException {
        repo.add("Harambe");
        repo.remove("Harambe");
        Assert.assertNotEquals("Harambe", repo.get((string) -> (string.equals("Harambe"))));
    }

    @Test(expected = ElementNotFoundException.class)
    public void testRemoveNotExisting() throws ElementNotFoundException {
        repo.remove("Harambe");
    }

    @Test(expected = Test.None.class)
    public void testUpdate() throws ElementExistsException, ElementNotFoundException {
        repo.add("Harambe");
        repo.update("Harambe", "The 2016 election");
        Assert.assertArrayEquals(repo.getAll().toArray(),
                Stream.of("The 2016 election").collect(Collectors.toList()).toArray());
    }

    @Test(expected = ElementNotFoundException.class)
    public void testUpdateNoElement() throws ElementNotFoundException {
        repo.update("Harambe", "The 2016 election");
    }

    @Test
    public void testGet() throws ElementExistsException {
        repo.add("Harambe");
        repo.add("The 2016 election");
        repo.add("Alex Jones");
        repo.add("Deez cojones");

        Assert.assertEquals("Harambe", repo.get((string) -> (string.equals("Harambe"))).get(0));
    }

}
