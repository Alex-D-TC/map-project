package com.company.Utils.Commands;

import com.company.Controller.PostController;
import com.company.Domain.Post;
import com.company.Utils.Exceptions.ElementNotFoundException;
import com.company.Utils.IOUtils;
import com.company.Utils.ReadUtils;

import javax.xml.bind.ValidationException;

/**
 * Created by AlexandruD on 10/14/2016.
 */
public class UpdatePost extends Command {

    public UpdatePost(PostController postController) {

        super("Update position", "Updates a position", () -> {

            int id = ReadUtils.readInt(IOUtils.getScannerInstanceOnIn(), "Input id: ");
            System.out.println("Input new post: ");
            Post post = ReadUtils.readPost(IOUtils.getScannerInstanceOnIn(), false);
            try {

                postController.update(new Post(id), new Post(id, post.getName(), post.getType()));
                return "Post updated successfully";

            }catch(ValidationException | ElementNotFoundException e) {
                return e.getMessage();
            }

        });

    }

}
