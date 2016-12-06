package com.company.Utils.IO.XML;

import javax.xml.stream.XMLStreamWriter;

/**
 * Created by AlexandruD on 12/6/2016.
 */
public interface XMLSerializer<T>  {
    void writeData(T element, XMLStreamWriter stream);
}
