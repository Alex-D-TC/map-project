package com.company.Utils.IO.XML;

import com.company.Domain.FisaPostElemDTO;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.security.InvalidParameterException;

/**
 * Created by AlexandruD on 12/6/2016.
 */
public class FisaPostXMLParser extends XMLParser<FisaPostElemDTO> {

    private static final String OBJ_NAME = FisaPostElemDTO.class.toString().substring(6);
    private boolean qPostID = false;
    private boolean qSarcinaID = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
        throws SAXException {

        if(qName.equalsIgnoreCase(OBJ_NAME)) {
            parsingItem = new FisaPostElemDTO();
            valid = true;
        }

        else if(qName.equalsIgnoreCase("PostID")) {
            if(valid) {
                qPostID = true;
            }
        }

        else if(qName.equalsIgnoreCase("SarcinaID")) {
            if(valid) {
                qSarcinaID = true;
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
            // Do nothing if the object has invalid data
            return;
        }

        if(qPostID) {
            try {
                int postID = Integer.parseInt(text);
                parsingItem.setPostID(postID);
            }catch(NumberFormatException e) {
                valid = false;
            }
            qPostID = false;
        }

        else if(qSarcinaID) {
            try {
                int sarcinaID = Integer.parseInt(text);
                parsingItem.setSarcinaID(sarcinaID);
            }catch(NumberFormatException e) {
                valid = false;
            }
            qSarcinaID = false;
        }
    }
}
