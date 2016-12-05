package com.company.Utils.Builders.RepositoryBuilder;

import com.company.Repository.CrudRepository;

import java.security.InvalidParameterException;

/**
 * Created by AlexandruD on 12/5/2016.
 */
public interface RepositoryBuilder<T> {

    CrudRepository<T> buildRepository() throws InvalidParameterException;
}
