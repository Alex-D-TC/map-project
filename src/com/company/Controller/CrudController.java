package com.company.Controller;

import com.company.Domain.Validator;
import com.company.Repository.Repository;
import com.company.Utils.Exceptions.ElementExistsException;
import com.company.Utils.Exceptions.ElementNotFoundException;

import javax.xml.bind.ValidationException;
import java.util.List;

/**
 * Created by AlexandruD on 10/23/2016.
 */
public abstract class CrudController<T> {

    private Repository<T> repo;
    private Validator<T> validator;


    public CrudController(Repository<T> repo,
                          Validator<T> validator) {
        this.repo = repo;
        this.validator = validator;
    }

    /**
     * Adds a new element
     * @param p - The element
     */
    public void add(T p) throws ValidationException, ElementExistsException {

        if(!validator.validate(p))
            throw new ValidationException("Invalid data");

        repo.add(p);
    }

    /**
     * Removes a element
     * @param p - The element to remove
     * @return - The removed element. Null if element is not found
     */
    public T remove(T p) throws ElementNotFoundException {
        return repo.remove(p);
    }

    /**
     * Updates a element
     * @param old - The old element
     * @param newP - The replacement
     */
    public void update(T old, T newP) throws ValidationException, ElementNotFoundException {

        if(!validator.validate(newP))
            throw new ValidationException("Invalid data");

        repo.update(old, newP);
    }

    /**
     * Returns all elements
     * @return - The list of all elements
     */
    public List<T> get() {
        return repo.getAll();
    }


}
