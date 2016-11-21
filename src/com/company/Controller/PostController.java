package com.company.Controller;

import com.company.Domain.Post;
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
public class PostController {

    private ObservableList<Post> model;
    private ObservableCrudService<Post> service;

    public PostController(ObservableCrudService<Post> _service) {
        service = _service;
        service.addListener((ignored) -> {
            model.setAll(StreamSupport.stream(service.getAll().spliterator(), false)
                    .collect(Collectors.toList()));
        });
        model = new ObservableListWrapper<>(StreamSupport.stream(service.getAll().spliterator(), false)
                                            .collect(Collectors.toList()));
    }

    public ObservableList<Post> getModel() {
        return model;
    }

    public void add(int id, String name, Post.Type type)
            throws ValidationException, ElementExistsException {

        Post p = new Post(id, name, type);
        service.add(p);
    }

    public void remove(int id) throws ElementNotFoundException {
        service.remove(new Post(id));
    }

    public void update(int id, String name, Post.Type type)
            throws ValidationException, ElementNotFoundException {

        Post newP = new Post(id, name, type);
        service.update(new Post(id), newP);
    }

}
