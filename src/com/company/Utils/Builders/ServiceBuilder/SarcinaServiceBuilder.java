package com.company.Utils.Builders.ServiceBuilder;

import com.company.Domain.Sarcina;
import com.company.Service.SarcinaService;

import java.security.InvalidParameterException;
import java.util.NoSuchElementException;

/**
 * Created by AlexandruD on 12/5/2016.
 */
public class SarcinaServiceBuilder extends ServiceBuilder<Sarcina> {

    @Override
    public SarcinaService buildService() throws InvalidParameterException {
        try {
            return new SarcinaService(repository.get(), validator.get());
        }catch(NoSuchElementException e) {
            throw new InvalidParameterException("Repository or validator invalid");
        }
    }
}
