package com.company.Utils.Commands;

import com.company.Controller.SarcinaController;
import com.company.Utils.Exceptions.ElementExistsException;
import com.company.Utils.IOUtils;
import com.company.Utils.ReadUtils;

import javax.xml.bind.ValidationException;

/**
 * Created by AlexandruD on 10/14/2016.
 */
public class AddSarcina extends Command {

    public AddSarcina(SarcinaController sarcinaController) {

        super("Add task", "Adds a task", () -> {

            try {
                sarcinaController.add(ReadUtils.readSarcina(IOUtils.getScannerInstanceOnIn()));
            }catch (ValidationException | ElementExistsException e) {
                return e.getMessage();
            }
            return "Sarcina added";
        });

    }

}
