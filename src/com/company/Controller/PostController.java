package com.company.Controller;

import com.company.Domain.Post;
import com.company.Service.PostService;
import com.company.Utils.Exceptions.ElementExistsException;
import com.company.Utils.Exceptions.ElementNotFoundException;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.ObservableList;

import javax.xml.bind.ValidationException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by AlexandruD on 11/20/2016.
 */
public class PostController {

    private ObservableList<Post> filterModel;
    private ObservableList<Post> model;
    private PostService service;
    private Optional<Post.Type> lastTypeFiltered;
    private Optional<String> lastStringFiltered;

    public PostController(PostService _service) {

        lastStringFiltered = Optional.empty();
        lastTypeFiltered = Optional.empty();

        service = _service;
        service.addListener((ignored) -> {
            // Model invalidation listener
            model.setAll(StreamSupport.stream(service.getAll().spliterator(), false)
                    .collect(Collectors.toList()));
        });

        service.addListener((ignored) -> {
            // Refilter
            filter(lastTypeFiltered, lastStringFiltered);
        });

        model = new ObservableListWrapper<>(StreamSupport.stream(service.getAll().spliterator(), false)
                                            .collect(Collectors.toList()));

        filterModel = new ObservableListWrapper<>(StreamSupport.stream(service.getAll().spliterator(), false)
                .collect(Collectors.toList()));
    }

    public ObservableList<Post> getModel() {
        return model;
    }

    public ObservableList<Post> getFilterModel() {
        return filterModel;
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

    public void filter(Optional<Post.Type> type, Optional<String> substring) {

        lastStringFiltered = substring;
        lastTypeFiltered = type;

        Iterable<Post> filterResult = service.filter(type, substring);

        if(!filterModel.subList(0, filterModel.size()).equals(filterResult)) {
            filterModel.setAll(
                    StreamSupport.stream(filterResult.spliterator(), false).collect(Collectors.toList()));
        }

    }



}
