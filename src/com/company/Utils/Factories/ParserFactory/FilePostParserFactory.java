package com.company.Utils.Factories.ParserFactory;

import com.company.Domain.Post;
import com.company.Utils.Parser;

/**
 * Created by AlexandruD on 12/5/2016.
 */
public class FilePostParserFactory implements ParserFactory<Post> {

    @Override
    public Parser<Post> buildParser(){
        return (line) -> {

            Post p = null;

            String[] tokens = line.split("[|]");

            if(tokens.length != 3) {
                return null;
            }

            int id;

            try {
                id = Integer.parseInt(tokens[0]);
            }catch(NumberFormatException e) {
                return null;
            }

            String name = tokens[1];
            Post.Type type = Post.stringToType(tokens[2]);

            if(type == null) {
                return null;
            }

            p = new Post(id, name, type);

            return p;

        };
    }
}
