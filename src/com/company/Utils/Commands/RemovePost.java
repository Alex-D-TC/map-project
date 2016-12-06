package com.company.Utils.Commands;

import com.company.Service.PostService;
import com.company.Domain.Post;
import com.company.Utils.Exceptions.ElementNotFoundException;
import com.company.Utils.IO.IOUtils;
import com.company.Utils.IO.ReadUtils;

/**
 * Created by AlexandruD on 10/14/2016.
 */
public class RemovePost extends Command {

    public RemovePost(PostService postController) {

        super("Remove position", "Removes a position", () -> {

            int id = ReadUtils.readInt(IOUtils.getScannerInstanceOnIn(), "Input id: ");
            try {

                Post post = postController.remove(new Post(id));

                return "Removed post: " + post.toString();

            }catch(ElementNotFoundException e) {
                return e.getMessage();
            }
        });

    }

}
