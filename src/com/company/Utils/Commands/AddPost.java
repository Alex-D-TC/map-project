package com.company.Utils.Commands;

import com.company.Controller.PostController;
import com.company.Utils.IOUtils;
import com.company.Utils.ReadUtils;

/**
 * Created by AlexandruD on 10/14/2016.
 */
public class AddPost extends Command {

    public AddPost(PostController postController) {

        super("Add position", "Adds a position", () -> {

            postController.add(ReadUtils.readPost(IOUtils.getScannerInstanceOnIn()));
            return "Post added";
        });

    }

}
