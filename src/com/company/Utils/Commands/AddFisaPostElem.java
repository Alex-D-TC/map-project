package com.company.Utils.Commands;

import com.company.Service.FisaPostService;
import com.company.Utils.Exceptions.ElementExistsException;
import com.company.Utils.IO.IOUtils;
import com.company.Utils.IO.ReadUtils;

import javax.xml.bind.ValidationException;

/**
 * Created by AlexandruD on 11/9/2016.
 */
public class AddFisaPostElem extends Command {

    public AddFisaPostElem(FisaPostService mediator) {

        super("Add JD element", "Adds a job description element", () -> {

            try {

                mediator.add(ReadUtils.readFisaPostElem(IOUtils.getScannerInstanceOnIn()));
                return "Job description element added successfully! :> ";

            }catch(ValidationException | ElementExistsException e) {
                return e.getMessage();
            }
        });

    }

}
