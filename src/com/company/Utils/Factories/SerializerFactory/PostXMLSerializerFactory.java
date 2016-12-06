package com.company.Utils.Factories.SerializerFactory;

import com.company.Domain.Post;
import com.company.Utils.IO.XML.XMLSerializer;

import javax.xml.stream.XMLStreamException;

/**
 * Created by AlexandruD on 12/6/2016.
 */
public class PostXMLSerializerFactory {

    public XMLSerializer<Post> newPostXMLSerializer() {
        return (elem, stream) -> {

            try {
                stream.writeStartElement(Post.class.toString().substring(6));
                stream.writeAttribute("id", Integer.toString(elem.getId()));

                stream.writeStartElement("Name");
                stream.writeCharacters(elem.getName());
                stream.writeEndElement();

                stream.writeStartElement("Type");
                stream.writeCharacters(Post.typeToString(elem.getType()));
                stream.writeEndElement();

                stream.writeEndElement();

            }catch(XMLStreamException e) {
                e.printStackTrace();
            }

        };
    }
}
