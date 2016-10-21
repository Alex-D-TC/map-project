package com.company.Utils.Commands;

import com.company.Controller.PostController;
import com.company.Domain.Post;
import com.company.Utils.IOUtils;
import com.company.Utils.ReadUtils;

/**
 * Created by AlexandruD on 10/14/2016.
 */
public class RemovePost extends Command {

    public RemovePost(PostController postController) {

        super("Remove position", "Removes a position", () -> {

            int id = ReadUtils.readInt(IOUtils.getScannerInstanceOnIn(), "Input id: ");
            Post post = postController.remove(new Post(id));
            String result = "No post found";

            if(post != null) {
                result = "Removed post: " + post.toString();
            }

            return result;
        });

    }

}
