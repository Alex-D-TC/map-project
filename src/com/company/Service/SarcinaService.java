package com.company.Service;

import com.company.Domain.Sarcina;
import com.company.Domain.Validator;
import com.company.Repository.CrudRepository;

import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * Created by AlexandruD on 10/8/2016.
 */
public class SarcinaService extends ObservableCrudService<Sarcina> {

    public SarcinaService(CrudRepository<Sarcina> repo, Validator<Sarcina> validator) {

        super(repo, validator);
    }

    /**
     * Returns all {@link Sarcina} whose descriptions contain the given substring
     * @param substring The substring
     * @return Al; {@link Sarcina} that mattch
     */
    public Iterable<Sarcina> getTasksBySubstring(String substring) {
        return get((sarcina) -> sarcina.getDescription().contains(substring));
    }

    /**
     * Returns the {@link Sarcina} whose id is equal to the given id
     * @param id The id
     * @return The {@link Sarcina}
     */
    public Optional<Sarcina> getTasksByID(int id) {
        return StreamSupport.stream(get((sarcina) -> (sarcina.getId() == id)).spliterator(), false)
                .reduce((a, b) -> (a));
    }

    /**
     * Gets the {@link Sarcina} sorted by their id
     * @return The sorted list
     */
    public Iterable<Sarcina> getSortedByID() {
        return getSorted((a, b) -> (a.getId() - b.getId()));
    }

}
