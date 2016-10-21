package com.company.Controller;

import com.company.Domain.Post;

import com.company.Domain.Validator;
import com.company.Repository.Repository;

import java.util.List;

/**
 * Created by AlexandruD on 10/8/2016.
 */
public class PostController {

    private Repository<Post> repo;
    private Validator<Post> validator;

    public PostController(Repository<Post> repo,
                          Validator<Post> validator) {
        this.repo = repo;
        this.validator = validator;
    }

    /**
     * Adds a new post
     * @param p - The post
     */
    public void add(Post p) {

        // TODO: SWITCH TO EXCEPTION THROWING
        if(!validator.validate(p))
            return;

        repo.add(p);
    }

    /**
     * Removes a post
     * @param p - The post to remove
     * @return - The removed post. Null if post is not found
     */
    public Post remove(Post p) {
        return repo.remove(p);
    }

    /**
     * Updates a post
     * @param old - The old post
     * @param newP - The replacement
     */
    public void update(Post old, Post newP) {

        // TODO: SWITCH TO EXCEPTION THROWING
        if(!validator.validate(newP))
            return;
        repo.update(old, newP);
    }

    /**
     * Returns all posts
     * @return - The list of all posts
     */
    public List<Post> get() {
        return repo.getAll();
    }

}
