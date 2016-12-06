package com.company.Utils.Factories.SerializerFactory;

import com.company.Domain.Sarcina;
import com.company.Utils.IO.XML.XMLSerializer;

import javax.xml.stream.XMLStreamException;

/**
 * Created by AlexandruD on 12/6/2016.
 */
public class SarcinaXMLSerializerFactory {

    public XMLSerializer<Sarcina> newSarcinaXMLSerializer() {
        return (elem, stream) -> {
            try {
                stream.writeStartElement(Sarcina.class.toString().substring(6));
                stream.writeAttribute("id", Integer.toString(elem.getId()));

                stream.writeStartElement("Description");
                stream.writeCharacters(elem.getDescription());
                stream.writeEndElement();

                stream.writeEndElement();
            }catch(XMLStreamException e) {
                e.printStackTrace();
            }
        };
    }
}
