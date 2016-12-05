package com.company.Utils.Factories.SerializerFactory;

import com.company.Utils.Serializer;

/**
 * Created by AlexandruD on 12/5/2016.
 */
public interface SerializerFactory<T> {

    Serializer<T> buildSerializer();
}
