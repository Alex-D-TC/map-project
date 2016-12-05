package com.company.Utils.Builders.ServiceBuilder;

import com.company.Domain.Post;
import com.company.Service.PostService;

import java.security.InvalidParameterException;
import java.util.NoSuchElementException;

/**
 * Created by AlexandruD on 12/5/2016.
 */
public class ObservablePostServiceBuilder extends ServiceBuilder<Post> {

    @Override
    public PostService buildService() throws InvalidParameterException {
        try {
            return new PostService(repository.get(), validator.get());
        }catch(NoSuchElementException e) {
            throw new InvalidParameterException("Repository or validator is null");
        }
    }
}
