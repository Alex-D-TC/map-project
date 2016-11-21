package com.company.Utils.Commands;

import com.company.Service.PostService;
import com.company.Domain.Post;
import com.company.Utils.IOUtils;
import com.company.Utils.ReadUtils;

import java.util.StringJoiner;
import java.util.stream.StreamSupport;

/**
 * Created by AlexandruD on 11/18/2016.
 */
public class GetPositionsByType extends Command {

    public GetPositionsByType(PostService controller) {
        super("Positions by type", "Gets all positions of a given type", () -> {

            Post.Type type = ReadUtils.readPostType(IOUtils.getScannerInstanceOnIn(), "Input type: ");
            Iterable<Post> positions = controller.getPositionsByType(type);
            StringJoiner joiner = new StringJoiner("\n");

            StreamSupport.stream(positions.spliterator(), false)
                         .forEach((post) -> joiner.add(post.toString()));

            return "Positions:\n" + joiner.toString();

        });
    }

}
