package com.company.Service;

import com.company.Domain.Validator;
import com.company.Repository.CrudRepository;
import com.company.Utils.Exceptions.ElementExistsException;
import com.company.Utils.Exceptions.ElementNotFoundException;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AlexandruD on 11/20/2016.
 */
public abstract class ObservableCrudService<T>
        extends CrudService<T>
        implements Observable {

    private List<InvalidationListener> listeners;

    public ObservableCrudService(CrudRepository<T> repo, Validator<T> validator) {

        super(repo, validator);
        listeners = new ArrayList<>();
    }

    @Override
    public void add(T elem) throws ValidationException, ElementExistsException {
        super.add(elem);
        notifyListeners();
    }

    @Override
    public T remove(T elem) throws ElementNotFoundException {
        super.remove(elem);
        notifyListeners();
        return elem;
    }

    @Override
    public void update(T old, T newP) throws ValidationException, ElementNotFoundException {
        super.update(old, newP);
        notifyListeners();
    }


    @Override
    public void removeListener(InvalidationListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void addListener(InvalidationListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        listeners.forEach((listener) -> listener.invalidated(this));
    }

}
