package com.company.Domain;

/**
 * Created by AlexandruD on 10/14/2016.
 */
public class SarcinaValidator extends Validator<Sarcina> {


    @Override
    public boolean validate(Sarcina s) {
        // Don't have much to validate bo$$
        if(s.getId() < 0)
            return false;
        return true;
    }

}
