package com.company.Utils.Commands;

import com.company.Service.SarcinaService;
import com.company.Domain.Sarcina;
import com.company.Utils.Exceptions.ElementNotFoundException;
import com.company.Utils.IO.IOUtils;
import com.company.Utils.IO.ReadUtils;

import javax.xml.bind.ValidationException;

/**
 * Created by AlexandruD on 10/14/2016.
 */
public class UpdateSarcina extends Command {

    public UpdateSarcina(SarcinaService sarcinaController) {

        super("Update task", "Updates a task", () -> {

            int id = ReadUtils.readInt(IOUtils.getScannerInstanceOnIn(), "Input id: ");
            System.out.println("Input new sarcina: ");
            Sarcina s = ReadUtils.readSarcina(IOUtils.getScannerInstanceOnIn(), false);

            try {

                sarcinaController.update(new Sarcina(id), new Sarcina(id, s.getDescription()));
                return "Sarcina updated successfully";

            }catch(ValidationException | ElementNotFoundException e) {
                return e.getMessage();
            }
        });

    }

}
