package com.company.Utils.Commands;

import com.company.Service.FisaPostService;
import com.company.Domain.Sarcina;

import java.util.List;
import java.util.StringJoiner;

/**
 * Created by AlexandruD on 11/9/2016.
 */
public class TopTasks extends Command {

    public TopTasks(FisaPostService mediator) {
        super("Top tasks", "Gets the top 3 assigned tasks among all JD elements", () -> {

            List<Sarcina> tasks = mediator.topTasks();

            StringJoiner joiner = new StringJoiner("\n");

            tasks.forEach((elem) -> joiner.add(elem.toString()));

            return joiner.toString();
        });
    }

}
