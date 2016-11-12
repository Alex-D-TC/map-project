package com.company.Controller;

import com.company.Domain.Post;

import com.company.Domain.Validator;
import com.company.Repository.CrudRepository;

/**
 * Created by AlexandruD on 10/8/2016.
 */
public class PostController extends CrudController<Post> {

    public PostController(CrudRepository<Post> repo, Validator<Post> validator) {
        super(repo, validator);
    }

}
