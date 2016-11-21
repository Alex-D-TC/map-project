package com.company.Utils.Commands;

import com.company.Service.PostService;
import com.company.Domain.Post;

import java.util.StringJoiner;
import java.util.stream.StreamSupport;

/**
 * Created by AlexandruD on 11/18/2016.
 */
public class GetPositionsSortedByID extends Command {

    public GetPositionsSortedByID(PostService controller) {

        super("Positions sorted ID", "Gets the positions sorted by id", () -> {
            Iterable<Post> positions = controller.getSortedByID();
            StringJoiner joiner = new StringJoiner("\n");

            StreamSupport.stream(positions.spliterator(), false)
                         .forEach((post) -> joiner.add(post.toString()));

            return "Positions:\n" + joiner.toString();
        });

    }

}
