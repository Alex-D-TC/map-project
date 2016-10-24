package com.company.Utils.Commands;

import com.company.Controller.SarcinaController;
import com.company.Domain.Sarcina;

import java.util.List;

/**
 * Created by AlexandruD on 10/14/2016.
 */
public class GetSarcini extends Command {

    public GetSarcini(SarcinaController sarcinaController) {

        super("Get tasks", "Gets all tasks", () -> {

            List<Sarcina> sarcini = sarcinaController.getAll();
            StringBuilder resString = new StringBuilder("Sarcini:\n");

            for(int i = 0; i < sarcini.size(); ++i) {
                resString.append(sarcini.get(i).toString() + '\n');
            }

            return resString.toString();
        });
    }

}
