package com.company.Utils.Builders.RepositoryBuilder;

import com.company.Repository.XMLRepository;
import com.company.Utils.IO.XML.XMLParser;
import com.company.Utils.IO.XML.XMLSerializer;

import java.nio.file.Path;
import java.security.InvalidParameterException;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Created by AlexandruD on 12/6/2016.
 */
public class XMLRepositoryBuilder<T> implements RepositoryBuilder<T> {

    private Optional<Path> filePath;
    private Optional<XMLParser<T>> parser;
    private Optional<XMLSerializer<T>> serializer;

    @Override
    public XMLRepository<T> buildRepository() throws InvalidParameterException {
        try {
            return new XMLRepository<>(filePath.get(), parser.get(), serializer.get());
        }catch(NoSuchElementException e) {
            throw new InvalidParameterException("FilePath, Parser, or Serializer are null");
        }
    }

    public void setFilePath(Path _filePath) {
        filePath = Optional.of(_filePath);
    }

    public void setParser(XMLParser<T> _parser) {
        parser = Optional.of(_parser);
    }

    public void setSerializer(XMLSerializer<T> _serializer) {
        serializer = Optional.of(_serializer);
    }
}
