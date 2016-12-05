package com.company.Utils.Factories.ParserFactory;

import com.company.Domain.FisaPostElemDTO;
import com.company.Utils.Factories.SerializerFactory.SerializerFactory;
import com.company.Utils.Serializer;

/**
 * Created by AlexandruD on 12/5/2016.
 */
public class FisaPostSerializerFactory implements SerializerFactory<FisaPostElemDTO> {

    @Override
    public Serializer<FisaPostElemDTO> buildSerializer() {
        return (elem) -> elem.getPostID() + "|" + elem.getSarcinaID();
    }
}
