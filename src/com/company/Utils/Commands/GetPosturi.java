package com.company.Utils.Commands;

import com.company.Controller.PostController;
import com.company.Domain.Post;

import java.util.List;

/**
 * Created by AlexandruD on 10/14/2016.
 */
public class GetPosturi extends Command {

    public GetPosturi(PostController postController) {
        super("Get positions", "Gets all positions", () -> {

            StringBuilder resString = new StringBuilder("Posturi:\n");

            postController.getAll().forEach((post) -> resString.append(post.toString() + '\n'));

            return resString.toString();

        });
    }

}
