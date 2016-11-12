package com.company.Domain;

/**
 * Created by AlexandruD on 11/9/2016.
 */
public class FisaPostElemValidator extends Validator<FisaPostElem> {

    @Override
    public boolean validate(FisaPostElem elem) {
        PostValidator pValidator = new PostValidator();
        SarcinaValidator sValidator = new SarcinaValidator();

        return elem.getId() > 0 &&
                pValidator.validate(elem.getPost()) &&
                sValidator.validate(elem.getSarcina());

    }

}
