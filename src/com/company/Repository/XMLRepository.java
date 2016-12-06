package com.company.Repository;

import com.company.Utils.Exceptions.ElementExistsException;
import com.company.Utils.Exceptions.ElementNotFoundException;
import com.company.Utils.IO.XML.XMLParser;
import com.company.Utils.IO.XML.XMLSerializer;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by AlexandruD on 12/6/2016.
 */
public class XMLRepository<T> extends CrudRepository<T> {

    private Path filePath;
    private XMLParser<T> parser;
    private XMLSerializer<T> serializer;

    public XMLRepository(Path _filePath, XMLParser<T> _parser, XMLSerializer<T> _serializer) {
        filePath = _filePath;
        parser = _parser;
        serializer = _serializer;
    }

    @Override
    public void add(T elem)
        throws ElementExistsException {

        List<T> elements = StreamSupport.stream(getAll().spliterator(), false).collect(Collectors.toList());
        if(elements.contains(elem)) {
            throw new ElementExistsException();
        }

        elements.add(elem);
        writeToFile(elements);
    }

    @Override
    public T remove(T elem)
        throws ElementNotFoundException {

        List<T> elements = StreamSupport.stream(getAll().spliterator(), false).collect(Collectors.toList());
        if(!elements.contains(elem)) {
            throw new ElementNotFoundException();
        }

        elem = elements.remove(elements.indexOf(elem));
        writeToFile(elements);

        return elem;
    }

    @Override
    public void update(T original, T newElem)
        throws ElementNotFoundException {

        List<T> elements = StreamSupport.stream(getAll().spliterator(), false).collect(Collectors.toList());
        if(!elements.contains(original)) {
            throw new ElementNotFoundException();
        }

        elements.remove(original);
        elements.add(newElem);

        writeToFile(elements);
    }

    @Override
    public Iterable<T> getAll() {

        try {

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser xml_parser = factory.newSAXParser();

            xml_parser.parse(new FileInputStream(filePath.toFile()), parser);

            return parser.getElements();

        }catch(FileNotFoundException | NoSuchFileException e) {
            return new ArrayList<>();
        }catch(IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }catch(Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

    }

    @Override
    public Iterable<T> get(Predicate<T> op) {
        return StreamSupport.stream(getAll().spliterator(), false)
                .filter(op)
                .collect(Collectors.toList());
    }

    private void writeToFile(List<T> elements) {

        XMLOutputFactory factory = XMLOutputFactory.newInstance();

        try {
            FileWriter writer = new FileWriter(filePath.toFile());
            XMLStreamWriter xmlWriter = factory.createXMLStreamWriter(writer);

            xmlWriter.writeStartDocument();
            xmlWriter.writeStartElement("elements");

            elements.forEach((elem) -> serializer.writeData(elem, xmlWriter));

            xmlWriter.writeEndElement();
            xmlWriter.writeEndDocument();

            xmlWriter.flush();
            xmlWriter.close();

        }catch(IOException | XMLStreamException e) {
            e.printStackTrace();
        }
    }

}
