package com.company.Utils.Commands;

import com.company.Service.SarcinaService;
import com.company.Domain.Sarcina;
import com.company.Utils.IOUtils;
import com.company.Utils.ReadUtils;

import java.util.Optional;

/**
 * Created by AlexandruD on 11/18/2016.
 */
public class GetTasksByID extends Command {

    public GetTasksByID(SarcinaService controller) {

        super("Tasks by ID", "Gets the task equal to the id", () -> {

            int id = ReadUtils.readInt(IOUtils.getScannerInstanceOnIn(), "Insert id: ");

            Optional<Sarcina> sarcOpt = controller.getTasksByID(id);

            try {
                return "Task:\n" + sarcOpt.get().toString();
            }catch(NullPointerException e) {
                return "No task found.";
            }

        });

    }

}
