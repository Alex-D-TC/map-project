package com.company.Utils.Builders.ServiceBuilder;

import com.company.Domain.Validator;
import com.company.Repository.CrudRepository;
import com.company.Service.CrudService;

import java.security.InvalidParameterException;
import java.util.Optional;

/**
 * Created by AlexandruD on 12/5/2016.
 */
public abstract class ServiceBuilder<T> {

    protected Optional<Validator<T>> validator;
    protected Optional<CrudRepository<T>> repository;

    public abstract CrudService<T> buildService() throws InvalidParameterException;

    public void setValidator(Validator<T> _validator) {
        validator = Optional.of(_validator);
    }

    public void setRepository(CrudRepository<T> _repository) {
        repository = Optional.of(_repository);
    }

}
