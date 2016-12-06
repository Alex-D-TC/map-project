package com.company.Utils.Factories.SerializerFactory;

import com.company.Domain.Post;
import com.company.Utils.IO.File.Serializer;

/**
 * Created by AlexandruD on 12/5/2016.
 */
public class PostSerializerFactory implements SerializerFactory<Post> {


    @Override
    public Serializer<Post> buildSerializer() {
        return (elem) -> elem.getId() + "|" + elem.getName() + "|" + Post.typeToString(elem.getType());
    }
}
