package com.company.Repository;

import java.util.function.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by AlexandruD on 10/14/2016.
 */
public class InMemoryRepository<T> implements Repository<T> {

    private List<T> elems;

    public InMemoryRepository() {
        elems = new ArrayList<>();
    }

    public List<T> getAll() {
        return new ArrayList<>(elems);
    }

    public void add(T s) {

        if(elems.indexOf(s) != -1)
            return;

        elems.add(s);
    }

    public List<T> get(Predicate<T> check) {

        return elems.stream().filter((post) ->
            check.test(post)).collect(Collectors.toList());
    }

    public void update(T oldT, T newT) {

        List<T> newElems = elems.stream()
                .map((elem) -> (elem.equals(oldT)? newT : elem))
                .collect(Collectors.toList());

        // TODO: THROW EXCEPTION FOR NO ELEMENT FOUND
        if(newElems.equals(elems)) {
            return;
        }

        elems = newElems;

    }

   public T remove(T post) {

        List<T> results = elems.stream()
                .filter((elem) -> (elem.equals(post)))
                .collect(Collectors.toList());

        T elem;

        if(results.size() != 0) {
            elem = results.get(0);
        } else {
            return null;
        }

        elems.remove(elem);

        return elem;
    }


}
