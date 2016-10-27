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
public class InMemoryRepository<T> extends CrudRepository<T> {

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

    public Iterable<T> get(Predicate<T> check) {

        return elems.stream().filter(check::test)
                    .collect(Collectors.toList());
    }

    public void update(T oldT, T newT) throws ElementNotFoundException {

        if(!elems.stream().anyMatch(oldT::equals)) {
            throw new ElementNotFoundException();
        }

        elems = elems.stream()
                .map((el) -> (el.equals(oldT)? newT : el))
                .collect(Collectors.toList());
    }

   public T remove(T post) throws ElementNotFoundException {

       if(!elems.stream().anyMatch(post::equals)) {
           throw new ElementNotFoundException();
       }

       T elem = elems.stream().reduce((ta, tb) -> ta.equals(post)? ta : tb).get();

       elems = elems.stream()
               .filter((el) -> (!el.equals(elem)))
               .collect(Collectors.toList());

        return elem;
    }


}
