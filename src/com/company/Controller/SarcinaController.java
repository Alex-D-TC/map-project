package com.company.Controller;

import com.company.Domain.Sarcina;
import com.company.Domain.Validator;
import com.company.Repository.Repository;

import java.util.List;

/**
 * Created by AlexandruD on 10/8/2016.
 */
public class SarcinaController {

    private Repository<Sarcina> repo;
    private Validator<Sarcina> validator;

    public SarcinaController(Repository<Sarcina> repo,
                             Validator<Sarcina> validator) {
        this.repo = repo;
        this.validator = validator;
    }

    /**
     * Adds a new task
     * @param s - The task
     */
    public void add(Sarcina s) {

        if(!validator.validate(s))
            return;

        repo.add(s);
    }

    /**
     * Gets all tasks
     * @return - A list of all tasks
     */
    public List<Sarcina> get() {
        return repo.getAll();
    }

    /**
     * Updates a task
     * @param old - The old task
     * @param newS - The replacement task
     */
    public void update(Sarcina old, Sarcina newS) {

        if(!validator.validate(newS))
            return;

        repo.update(old, newS);
    }

    /**
     * Removes a task
     * @param s - The task to remove
     * @return - The removed task. Null if the task is not found
     */
    public Sarcina remove(Sarcina s) {

        return repo.remove(s);
    }

}
