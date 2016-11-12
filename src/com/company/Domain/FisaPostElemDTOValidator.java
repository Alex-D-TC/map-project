package com.company.Domain;

/**
 * Created by AlexandruD on 11/10/2016.
 */
public class FisaPostElemDTOValidator extends Validator<FisaPostElemDTO> {

    @Override
    public boolean validate(FisaPostElemDTO elem) {
        return elem.getPostID() >= 0 && elem.getSarcinaID() >= 0;
    }

}
