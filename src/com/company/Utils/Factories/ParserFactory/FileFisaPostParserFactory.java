package com.company.Utils.Factories.ParserFactory;

import com.company.Domain.FisaPostElemDTO;
import com.company.Utils.IO.File.Parser;

/**
 * Created by AlexandruD on 12/5/2016.
 */
public class FileFisaPostParserFactory implements ParserFactory<FisaPostElemDTO> {

    @Override
    public Parser<FisaPostElemDTO> buildParser() {
        return (line) -> {
            String[] lineSplit = line.split("[|]");

            if(lineSplit.length != 2)
                return null;

            try {

                int postID = Integer.parseInt(lineSplit[0]),
                        taskID = Integer.parseInt(lineSplit[1]);

                return new FisaPostElemDTO(postID, taskID);

            }catch(NumberFormatException e) {
                return null;
            }

        };
    }

}
