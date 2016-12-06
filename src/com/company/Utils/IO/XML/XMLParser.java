package com.company.Utils.IO.XML;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AlexandruD on 12/6/2016.
 */
public abstract class XMLParser<T> extends DefaultHandler {

    protected List<T> elements = new ArrayList<>();
    protected T parsingItem;
    protected boolean valid = false;

    public abstract void startElement(String uri, String localName,String qName,
                             Attributes attributes) throws SAXException;

    public abstract void endElement(String uri, String localName,
                           String qName) throws SAXException;

    public abstract void characters(char ch[], int start, int length) throws SAXException;

    public List<T> getElements() {
        List<T> elemCopy = new ArrayList<>(elements);
        elements.clear();
        return elemCopy;
    }

}
