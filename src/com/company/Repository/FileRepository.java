package com.company.Repository;

import com.company.Utils.Exceptions.ElementExistsException;
import com.company.Utils.Exceptions.ElementNotFoundException;
import com.company.Utils.Parser;

import java.util.function.Predicate;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by AlexandruD on 10/14/2016.
 */
public class FileRepository<T> extends CrudRepository<T> {

    private String filePath;
    private Parser<T> parser;

    public FileRepository(String filePath, Parser<T> parser) {
        this.filePath = filePath;
        this.parser = parser;
    }

    @Override
    public void add(T elem) throws ElementExistsException {

        if(get((T item) -> (item.equals(elem))).size() != 0) {
            throw new ElementExistsException();
        }

        try(BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true)))) {

            writer.write(elem.toString() + '\n');

        }catch(IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public T remove(T rElem) throws ElementNotFoundException {

        List<T> readItems = getAll();

        int index = readItems.indexOf(rElem);

        if(index == -1) {
            throw new ElementNotFoundException();
        }

        T elem = readItems.remove(readItems.indexOf(rElem));

        writeToFile(readItems);

        return elem;
    }

    @Override
    public List<T> getAll() {

        List<T> elems = new ArrayList<>();

        try(BufferedReader reader =
                    new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {

            T elem;
            while((elem = parser.parse(reader)) != null) {
                elems.add(elem);
            }

        }catch(IOException e) {
            e.printStackTrace();
        }

        return elems;

    }

    @Override
    public List<T> get(Predicate<T> op) {

        List<T> elements = new ArrayList<>();

        try(BufferedReader reader =
                    new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {

            T elem;
            while((elem = parser.parse(reader)) != null) {
                if(op.test(elem)) {
                    elements.add(elem);
                }
            }

        }catch(IOException e ) {
            e.printStackTrace();
        }

        return elements;
    }

    @Override
    public void update(T original, T newElem) throws ElementNotFoundException {

        List<T> elems = getAll();
        int elemCount = elems.size();

        elems = elems.stream()
                .filter((elem) -> (!elem.equals(original)))
                .collect(Collectors.toList());

        if(elemCount == elems.size()) {
            throw new ElementNotFoundException();
        }

        elems.add(newElem);
        writeToFile(elems);
    }

    /**
     * Writes a list of items to the file. Writing is NOT in append mode.
     * @param elements The list of elements
     */
    private void writeToFile(List<T> elements) {

        try(BufferedWriter writer =
                    new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)))) {

            for(T elem : elements) {
                writer.write(elem.toString());
            }

        }catch(IOException e) {
            e.printStackTrace();
        }

    }

}
