package com.company.Utils.Commands;

import com.company.Service.SarcinaService;
import com.company.Domain.Sarcina;

import java.util.StringJoiner;
import java.util.stream.StreamSupport;

/**
 * Created by AlexandruD on 11/18/2016.
 */
public class GetTasksSortedByID extends Command {

    public GetTasksSortedByID(SarcinaService controller) {

        super("Tasks sorted ID", "Gets tasks sorted by id", () -> {
            Iterable<Sarcina> tasks = controller.getSortedByID();
            StringJoiner joiner = new StringJoiner("\n");

            StreamSupport.stream(tasks.spliterator(), false)
                         .forEach((sarcina) -> joiner.add(sarcina.toString()));

            return "Sarcini:\n" + joiner.toString();

        });

    }

}
