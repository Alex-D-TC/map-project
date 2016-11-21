package com.company.Controller;

import com.company.Domain.Sarcina;
import com.company.Service.ObservableCrudService;
import com.company.Utils.Exceptions.ElementExistsException;
import com.company.Utils.Exceptions.ElementNotFoundException;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.ObservableList;

import javax.xml.bind.ValidationException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by AlexandruD on 11/20/2016.
 */
public class SarcinaController {

    private ObservableList<Sarcina> model;
    private ObservableCrudService<Sarcina> service;

    public SarcinaController(ObservableCrudService<Sarcina> _service) {
        service = _service;
        service.addListener((ignored) -> model.setAll(
                StreamSupport.stream(service.getAll().spliterator(), false)
                             .collect(Collectors.toList())
        ));
        model = new ObservableListWrapper<>(
                StreamSupport.stream(service.getAll().spliterator(), false)
                              .collect(Collectors.toList())
        );
    }

    public ObservableList<Sarcina> getModel() {
        return model;
    }

    public void add(int id, String description)
        throws ValidationException, ElementExistsException {

        service.add(new Sarcina(id, description));
    }

    public void remove(int id) throws ElementNotFoundException {
        service.remove(new Sarcina(id));
    }

    public void update(int id, String description) throws ValidationException, ElementNotFoundException {
        service.update(new Sarcina(id), new Sarcina(id, description));
    }

}
