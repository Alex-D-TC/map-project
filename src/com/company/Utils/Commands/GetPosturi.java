package com.company.Utils.Commands;

import com.company.Service.PostService;

/**
 * Created by AlexandruD on 10/14/2016.
 */
public class GetPosturi extends Command {

    public GetPosturi(PostService postController) {
        super("Get positions", "Gets all positions", () -> {

            StringBuilder resString = new StringBuilder("Posturi:\n");

            postController.getAll().forEach((post) -> resString.append(post.toString() + '\n'));

            return resString.toString();

        });
    }

}
