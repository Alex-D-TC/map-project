package com.company.Utils.Commands;

import com.company.Service.SarcinaService;
import com.company.Domain.Sarcina;
import com.company.Utils.Exceptions.ElementNotFoundException;
import com.company.Utils.IOUtils;
import com.company.Utils.ReadUtils;

/**
 * Created by AlexandruD on 10/14/2016.
 */
public class RemoveSarcina extends Command {

    public RemoveSarcina(SarcinaService sarcinaController) {

        super("Remove task", "Removes a task", () -> {

            int id = ReadUtils.readInt(IOUtils.getScannerInstanceOnIn(), "Input id: ");
            try {

                Sarcina sarc = sarcinaController.remove(new Sarcina(id));
                return "Removed sarcina: " + sarc.toString();

            }catch(ElementNotFoundException e) {
                return e.getMessage();
            }
        });
    }

}
