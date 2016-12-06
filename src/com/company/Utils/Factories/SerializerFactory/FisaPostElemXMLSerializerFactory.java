package com.company.Utils.Factories.SerializerFactory;

import com.company.Domain.FisaPostElemDTO;
import com.company.Utils.IO.XML.XMLSerializer;

import javax.xml.stream.XMLStreamException;

/**
 * Created by AlexandruD on 12/6/2016.
 */
public class FisaPostElemXMLSerializerFactory {

    public XMLSerializer<FisaPostElemDTO> newFisaPostXMLSerializer() {

        return (elem, stream) -> {
          try {
              stream.writeStartElement(FisaPostElemDTO.class.toString().substring(6));

              stream.writeStartElement("PostID");
              stream.writeCharacters(Integer.toString(elem.getPostID()));
              stream.writeEndElement();

              stream.writeStartElement("SarcinaID");
              stream.writeCharacters(Integer.toString(elem.getSarcinaID()));
              stream.writeEndElement();

              stream.writeEndElement();
          }catch(XMLStreamException e) {
              e.printStackTrace();
          }
        };
    }
}
