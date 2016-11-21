package com.company.Utils.Commands;

import com.company.Service.PostService;
import com.company.Utils.Exceptions.ElementExistsException;
import com.company.Utils.IOUtils;
import com.company.Utils.ReadUtils;

import javax.xml.bind.ValidationException;

/**
 * Created by AlexandruD on 10/14/2016.
 */
public class AddPost extends Command {

    public AddPost(PostService postController) {

        super("Add position", "Adds a position", () -> {

            try {
                postController.add(ReadUtils.readPost(IOUtils.getScannerInstanceOnIn()));
            }catch (ValidationException | ElementExistsException e) {
                return e.getMessage();
            }

            return "Post added";
        });

    }

}
