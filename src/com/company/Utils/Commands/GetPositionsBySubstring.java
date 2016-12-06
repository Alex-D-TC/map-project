package com.company.Utils.Commands;

import com.company.Service.PostService;
import com.company.Domain.Post;
import com.company.Utils.IO.IOUtils;
import com.company.Utils.IO.ReadUtils;

import java.util.StringJoiner;
import java.util.stream.StreamSupport;

/**
 * Created by AlexandruD on 11/18/2016.
 */
public class GetPositionsBySubstring extends Command {

    public GetPositionsBySubstring(PostService controller) {
        super("Positions by substring", "Gets all positions matching a substring", () -> {

            String substring = ReadUtils.readString(IOUtils.getScannerInstanceOnIn(), "Input substring: ");
            Iterable<Post> positions = controller.getPositionsBySubstring(substring);
            StringJoiner joiner = new StringJoiner("\n");

            StreamSupport.stream(positions.spliterator(), false)
                         .forEach((post) -> joiner.add(post.toString()));

            return "Posts:\n" + joiner.toString();
        });
    }

}
