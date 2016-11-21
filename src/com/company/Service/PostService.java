package com.company.Service;

import com.company.Domain.Post;

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
 * Created by AlexandruD on 10/8/2016.
 */
public class PostService extends ObservableCrudService<Post> {

    public PostService(CrudRepository<Post> repo, Validator<Post> validator) {
        super(repo, validator);
    }

    /**
     * Gets all {@link Post} whose name contain the given substring
     * @param substring The substring
     * @return The {@link Post} of which match
     */
    public Iterable<Post> getPositionsBySubstring(String substring) {
        return get((post) -> (post.getName().contains(substring)));
    }

    /**
     * Gets all {@link Post} of the given {@link Post.Type}
     * @param type The type
     * @return The posts of the given {@link Post.Type}
     */
    public Iterable<Post> getPositionsByType(Post.Type type) {
        return get((post) -> (post.getType().equals(type)));
    }

    /**
     * Gets the {@link Post} sorted by their id
     * @return The sorted list
     */
    public Iterable<Post> getSortedByID() {
        return getSorted((a, b) -> (a.getId() - b.getId()));
    }

}
