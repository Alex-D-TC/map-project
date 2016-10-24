package com.company.Utils.Commands;

import com.company.Controller.PostController;
import com.company.Domain.Post;

import java.util.List;

/**
 * Created by AlexandruD on 10/14/2016.
 */
public class GetPosturi extends Command {

    public GetPosturi(PostController postController) {
        super("Get posts", "Gets all posts", () -> {

            StringBuilder resString = new StringBuilder("Posturi:\n");
            List<Post> posturi = postController.getAll();
            for(int i = 0; i < posturi.size(); ++i) {
                resString.append(posturi.get(i).toString() + '\n');
            }
            return resString.toString();

        });
    }

}
