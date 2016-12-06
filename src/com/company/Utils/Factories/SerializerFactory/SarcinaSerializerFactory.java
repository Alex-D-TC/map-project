package com.company.Utils.Factories.SerializerFactory;

import com.company.Domain.Sarcina;
import com.company.Utils.IO.File.Serializer;

/**
 * Created by AlexandruD on 12/5/2016.
 */
public class SarcinaSerializerFactory implements SerializerFactory<Sarcina> {

    @Override
    public Serializer<Sarcina> buildSerializer() {
        return (elem) -> elem.getId() + "|" + elem.getDescription();
    }
}
