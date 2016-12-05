package com.company.Utils.Commands;

import com.company.Service.FisaPostService;
import com.company.Domain.Sarcina;
import com.company.Utils.IOUtils;
import com.company.Utils.ReadUtils;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by AlexandruD on 11/9/2016.
 */
public class GetFisaPost extends Command {

    public GetFisaPost(FisaPostService mediator) {

        super("Get job description", "Gets the job description of a position", () -> {

            int id = ReadUtils.readInt(IOUtils.getScannerInstanceOnIn(), "Input position id");
            List<Sarcina> resultList =
                    StreamSupport.stream(mediator.getTasksOfBindings((elem) -> (elem.getPostID() == id))
                                        .spliterator(), false)
                                        .collect(Collectors.toList());

            if(resultList.size() == 0) {
                return "No elements found for the position id";
            }

            StringJoiner joiner = new StringJoiner("\n");

            resultList.stream()
                      .map((elem) -> (elem.toString()))
                      .forEach(joiner::add);

            return joiner.toString();
        });

    }

}
