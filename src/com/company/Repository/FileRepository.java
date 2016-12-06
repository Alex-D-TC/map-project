package com.company.Repository;

import com.company.Utils.Exceptions.ElementExistsException;
import com.company.Utils.Exceptions.ElementNotFoundException;
import com.company.Utils.IO.File.Parser;
import com.company.Utils.IO.File.Serializer;

import java.nio.file.*;
import java.util.function.Predicate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by AlexandruD on 10/14/2016.
 */
public class FileRepository<T> extends CrudRepository<T> {

    private Path filePath;
    private Parser<T> parser;
    private Serializer<T> serializer;
    private final String serializedPath;

    public FileRepository(String filePath, Parser<T> parser, Serializer<T> serializer) {
        this.filePath = Paths.get(filePath);
        this.parser = parser;
        this.serializer = serializer;
        serializedPath = filePath + "_serialized.txt";
    }

    @Override
    public void add(T elem) throws ElementExistsException {

        if(StreamSupport.stream(get((T item) -> (item.equals(elem))).spliterator(), false).count() != 0) {
            throw new ElementExistsException();
        }

        try {

            Files.write(filePath, Stream.of(elem)
                    .map(serializer::serialize)
                    .collect(Collectors.toList()),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);

            serializeElement(elem);

        }catch(IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public T remove(T rElem) throws ElementNotFoundException {

        List<T> readItems = StreamSupport.stream(getAll().spliterator(), false)
                                        .collect(Collectors.toList());

        int index = readItems.indexOf(rElem);

        if(index == -1) {
            throw new ElementNotFoundException();
        }

        T elem = readItems.remove(readItems.indexOf(rElem));

        writeToFile(readItems);

        return elem;
    }

    @Override
    public Iterable<T> getAll() {
        try {

            return Files.lines(filePath)
                    .map(parser::parse)
                    .filter((elem) -> (elem != null)) // Our parser can return null
                    .collect(Collectors.toList());

        }catch(NoSuchFileException e) {
            return new ArrayList<>();
        }catch(IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    public Iterable<T> get(Predicate<T> op) {

        try {

            return Files.lines(filePath)
                    .map(parser::parse)
                    .filter((elem) -> (elem != null)) // Our parser can return null
                    .filter(op)
                    .collect(Collectors.toList());

        }catch(NoSuchFileException e) {
            return new ArrayList<>();
        }catch(IOException e ) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void update(T original, T newElem) throws ElementNotFoundException {

        if(StreamSupport.stream(get((elem) -> (elem.equals(original))).spliterator(), false).count() == 0) {
            throw new ElementNotFoundException();
        }

        writeToFile(StreamSupport.stream(getAll().spliterator(), false)
                .map((elem) -> elem.equals(original)? newElem : elem)
                .collect(Collectors.toList()));
    }

    /**
     * Writes a list of items to the file. Writing is NOT in append mode.
     * @param elements The list of elements
     */
    private void writeToFile(List<T> elements) {

        try {
            Files.write(filePath, elements.stream()
                                .map(serializer::serialize)
                                .collect(Collectors.toList()));
            serializeAll(elements);
        }catch(IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Adds a single serialized element to the file
     * @param elem - The element
     */
    private void serializeElement(T elem) {

        if(!Files.exists(Paths.get(serializedPath))) {
            try {
                Files.createFile(Paths.get(serializedPath));
            }catch(IOException e) {
                e.printStackTrace();
            }
        }

        try(ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(serializedPath, true))) {
            stream.writeObject(elem);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Serializes a list of elements to the file
     * @param elements The elements to serialize
     */
    private void serializeAll(List<T> elements) {

        try(ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(serializedPath))) {

            elements.forEach((elem) -> {
                try {
                    stream.writeObject(elem);
                }catch(IOException e) {
                    e.printStackTrace();
                }
                });

        }catch(IOException e) {
            e.printStackTrace();
        }

    }

}
