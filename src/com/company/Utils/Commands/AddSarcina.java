package com.company.Utils.Commands;

import com.company.Controller.SarcinaController;
import com.company.Utils.IOUtils;
import com.company.Utils.ReadUtils;

/**
 * Created by AlexandruD on 10/14/2016.
 */
public class AddSarcina extends Command {

    public AddSarcina(SarcinaController sarcinaController) {

        super("Add task", "Adds a task", () -> {

            sarcinaController.add(ReadUtils.readSarcina(IOUtils.getScannerInstanceOnIn()));
            return "Sarcina added";
        });

    }

}
