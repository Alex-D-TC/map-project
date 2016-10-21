package com.company.Utils;

import java.io.BufferedReader;

/**
 * Created by AlexandruD on 10/14/2016.
 */
public interface Parser<T> {

    T parse(BufferedReader stream);
}
