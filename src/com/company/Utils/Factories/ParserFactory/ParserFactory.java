package com.company.Utils.Factories.ParserFactory;

import com.company.Utils.IO.File.Parser;

/**
 * Created by AlexandruD on 12/5/2016.
 */
public interface ParserFactory<T> {
    Parser<T> buildParser();
}
