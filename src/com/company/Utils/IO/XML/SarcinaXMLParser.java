package com.company.Utils.IO.XML;

import com.company.Domain.Sarcina;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.security.InvalidParameterException;

/**
 * Created by AlexandruD on 12/6/2016.
 */
public class SarcinaXMLParser extends XMLParser<Sarcina> {

    private static final String OBJ_NAME = Sarcina.class.toString().substring(6);
    private boolean qDesc = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
        throws SAXException {

        if(qName.equalsIgnoreCase(OBJ_NAME)) {
            try {
                String id = attributes.getValue("id");
                if(id == null) {
                    throw new InvalidParameterException();
                }
                parsingItem = new Sarcina();
                parsingItem.setId(Integer.parseInt(id));
                valid = true;
            }catch(NumberFormatException | InvalidParameterException e) {
                valid = false;
            }
        }

        else if(qName.equalsIgnoreCase("Description")) {
            if(valid) {
                qDesc = true;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
        throws SAXException {

        if(qName.equalsIgnoreCase(OBJ_NAME)) {
            if(valid) {
                elements.add(parsingItem);
                valid = false;
            }
        }
    }

    @Override
    public void characters(char ch[], int start, int length)
        throws SAXException {

        String text = new String(ch, start, length);

        if(!valid) {
            // Why bother if the object has invalid data
            return;
        }

        if(qDesc) {
            parsingItem.setDescription(text);
            qDesc = false;
        }
    }

}
