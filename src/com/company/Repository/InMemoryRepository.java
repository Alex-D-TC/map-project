package com.company.Repository;

import com.company.Utils.Exceptions.ElementExistsException;
import com.company.Utils.Exceptions.ElementNotFoundException;

import java.util.function.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by AlexandruD on 10/14/2016.
 */
public class InMemoryRepository<T> extends Repository<T> {

    private List<T> elems;

    public InMemoryRepository() {
        elems = new ArrayList<>();
    }

    public List<T> getAll() {
        return new ArrayList<>(elems);
    }

    public void add(T s) throws ElementExistsException {

        if(elems.indexOf(s) != -1)
            throw new ElementExistsException();

        elems.add(s);
    }

    public List<T> get(Predicate<T> check) {

        return elems.stream().filter(check::test)
                    .collect(Collectors.toList());
    }

    public void update(T oldT, T newT) throws ElementNotFoundException {

        List<T> newElems = elems.stream()
                .map((elem) -> (elem.equals(oldT)? newT : elem))
                .collect(Collectors.toList());

        if(newElems.equals(elems)) {
            throw new ElementNotFoundException();
        }

        elems = newElems;
    }

   public T remove(T post) throws ElementNotFoundException {

        List<T> results = elems.stream()
                .filter((elem) -> (elem.equals(post)))
                .collect(Collectors.toList());

        T elem;

        if(results.size() != 0) {
            elem = results.get(0);
        } else {
            throw new ElementNotFoundException();
        }

        elems.remove(elem);

        return elem;
    }


}
