package com.company.Utils.Commands;

import com.company.Service.SarcinaService;

/**
 * Created by AlexandruD on 10/14/2016.
 */
public class GetSarcini extends Command {

    public GetSarcini(SarcinaService sarcinaController) {

        super("Get tasks", "Gets all tasks", () -> {

            StringBuilder resString = new StringBuilder("Sarcini:\n");

            sarcinaController.getAll().forEach((sarc) -> resString.append(sarc.toString() + '\n'));

            return resString.toString();
        });
    }

}
