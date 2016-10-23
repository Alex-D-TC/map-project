package com.company.Controller;

import com.company.Domain.Sarcina;
import com.company.Domain.Validator;
import com.company.Repository.Repository;

/**
 * Created by AlexandruD on 10/8/2016.
 */
public class SarcinaController extends CrudController<Sarcina> {


    public SarcinaController(Repository<Sarcina> repo, Validator<Sarcina> validator) {
        super(repo, validator);
    }

}
