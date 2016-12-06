package com.company.Utils.Commands;

import com.company.Service.SarcinaService;
import com.company.Domain.Sarcina;
import com.company.Utils.IO.IOUtils;
import com.company.Utils.IO.ReadUtils;

import java.util.StringJoiner;
import java.util.stream.StreamSupport;

/**
 * Created by AlexandruD on 11/18/2016.
 */
public class GetTasksBySubstring extends Command {

    public GetTasksBySubstring(SarcinaService controller) {
        super("Task by substring","Gets all tasks containing a substring", () ->

            {

                String substring = ReadUtils.readString(IOUtils.getScannerInstanceOnIn(), "Input substring: ");
                Iterable<Sarcina> sarcini = controller.getTasksBySubstring(substring);

                StringJoiner joiner = new StringJoiner("\n");

                StreamSupport.stream(sarcini.spliterator(), false)
                             .forEach((sarcina) -> joiner.add(sarcina.toString()));

                return "Found tasks:\n" + joiner.toString();
            }

        );
    }

}
