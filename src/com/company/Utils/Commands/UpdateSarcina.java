package com.company.Utils.Commands;

import com.company.Controller.SarcinaController;
import com.company.Domain.Sarcina;
import com.company.Utils.IOUtils;
import com.company.Utils.ReadUtils;

/**
 * Created by AlexandruD on 10/14/2016.
 */
public class UpdateSarcina extends Command {

    public UpdateSarcina(SarcinaController sarcinaController) {

        super("Update task", "Updates a task", () -> {

            int id = ReadUtils.readInt(IOUtils.getScannerInstanceOnIn(), "Input id: ");
            System.out.println("Input new sarcina: ");
            Sarcina s = ReadUtils.readSarcina(IOUtils.getScannerInstanceOnIn(), false);
            sarcinaController.update(new Sarcina(id), new Sarcina(id, s.getDescription()));

            return "Sarcina updated successfully";
        });

    }

}
