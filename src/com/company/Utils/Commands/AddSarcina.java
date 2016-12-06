package com.company.Utils.Commands;

import com.company.Service.SarcinaService;
import com.company.Utils.Exceptions.ElementExistsException;
import com.company.Utils.IO.IOUtils;
import com.company.Utils.IO.ReadUtils;

import javax.xml.bind.ValidationException;

/**
 * Created by AlexandruD on 10/14/2016.
 */
public class AddSarcina extends Command {

    public AddSarcina(SarcinaService sarcinaController) {

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
