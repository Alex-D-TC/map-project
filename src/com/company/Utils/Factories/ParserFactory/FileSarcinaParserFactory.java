package com.company.Utils.Factories.ParserFactory;

import com.company.Domain.Sarcina;
import com.company.Utils.Parser;

/**
 * Created by AlexandruD on 12/5/2016.
 */
public class FileSarcinaParserFactory implements ParserFactory<Sarcina> {

    @Override
    public Parser<Sarcina> buildParser() {
        return (line) -> {
            Sarcina s = null;

            String[] tokens = line.split("[|]");

            if(tokens.length != 2) {
                return null;
            }

            int id;
            String description;

            try {
                id = Integer.parseInt(tokens[0]);
                description = tokens[1];
            } catch(NumberFormatException e) {
                return null;
            }

            s = new Sarcina(id, description);

            return s;
        };
    }
}
