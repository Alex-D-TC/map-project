package com.company.Utils.Commands;

import com.company.Controller.SarcinaController;
import com.company.Domain.Sarcina;
import com.company.Utils.IOUtils;
import com.company.Utils.ReadUtils;

/**
 * Created by AlexandruD on 10/14/2016.
 */
public class RemoveSarcina extends Command {

    public RemoveSarcina(SarcinaController sarcinaController) {

        super("Remove task", "Removes a task", () -> {

            int id = ReadUtils.readInt(IOUtils.getScannerInstanceOnIn(), "Input id: ");
            Sarcina sarc = sarcinaController.remove(new Sarcina(id));
            String result = "No sarcina found";
            if(sarc != null) {
                result = "Removed sarcina: " + sarc.toString();
            }

            return result;
        });
    }

}
