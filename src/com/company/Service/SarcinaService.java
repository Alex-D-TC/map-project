package com.company.Service;

import com.company.Domain.Sarcina;
import com.company.Domain.Validator;
import com.company.Repository.CrudRepository;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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

    public Iterable<Sarcina> filter(Optional<String> substring) {
        return fin(filterSubstring(substring));
    }

    /**
     * Final component of a composite filtering. Used to apply the filtering result to the filter model.
     * Example usage: fin(filterSubstring('ada'))
     * @param stream The stream
     */
    private Iterable<Sarcina> fin(Stream<Sarcina> stream) {
        return stream.collect(Collectors.toList());
    }

    /**
     * Used as a starting filter for composite filtering. Use as argument on a fin call,
     * or an argument on a subsequent filter call.
     * @param substring The substring
     * @return A stream of the filtered elements
     */
    private Stream<Sarcina> filterSubstring(Optional<String> substring) {

        Stream<Sarcina> stream = StreamSupport.stream(getAll().spliterator(), false);

        if(!substring.isPresent())
            return stream;

        return stream.filter((elem) -> elem.getDescription().contains(substring.get()));
    }

    /**
     * Used as an intermediary for composite filtering. Use as argument on a fin call.
     * @param substring The substring
     * @param stream The partial stream
     * @return A stream of the filtered elements
     */
    private Stream<Sarcina> filterSubstring(Optional<String> substring, Stream<Sarcina> stream) {

        if(!substring.isPresent())
            return stream;

        return stream.filter((elem) -> elem.getDescription().contains(substring.get()));
    }

}
