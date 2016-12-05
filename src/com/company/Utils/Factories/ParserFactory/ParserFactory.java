package com.company.Utils.Factories.ParserFactory;

import com.company.Utils.Parser;

/**
 * Created by AlexandruD on 12/5/2016.
 */
public interface ParserFactory<T> {
    Parser<T> buildParser();
}
