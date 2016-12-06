package com.company.Utils.IO.XML;

import com.company.Domain.Post;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.security.InvalidParameterException;

/**
 * Created by AlexandruD on 12/6/2016.
 */
public class PostXMLParser extends XMLParser<Post> {

    private static final String OBJ_NAME = Post.class.toString().substring(6);
    private boolean qNam = false;
    private boolean qType = false;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
        throws SAXException {

        if(qName.equalsIgnoreCase(OBJ_NAME)) {
            try {
                String id = attributes.getValue("id");
                if(id == null) {
                    throw new InvalidParameterException();
                }
                parsingItem = new Post();
                parsingItem.setId(Integer.parseInt(id));
                valid = true;
            }catch(NumberFormatException | InvalidParameterException e) {
                valid = false;
            }
        }

        else if(qName.equalsIgnoreCase("Name")) {
            if(valid) {
                qNam = true;
            }
        }

        else if(qName.equalsIgnoreCase("Type")) {
            if(valid) {
                qType = true;
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
            // Why bother if the overall object has invalid data
            return;
        }

        if(qNam) {
            parsingItem.setName(text);
            qNam = false;
        }

        else if(qType) {
            Post.Type type = Post.stringToType(text);
            if(type == null) {
                valid = false;
            } else {
                parsingItem.setType(type);
            }
            qType = false;
        }

    }


}
