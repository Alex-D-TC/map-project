package com.company.Utils.Builders.RepositoryBuilder;

import com.company.Repository.FileRepository;
import com.company.Utils.IO.File.Parser;
import com.company.Utils.IO.File.Serializer;

import java.security.InvalidParameterException;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Created by AlexandruD on 12/5/2016.
 */
public class FileRepositoryBuilder<T> implements RepositoryBuilder<T> {

    private Optional<String> filePath;
    private Optional<Parser<T>> parser;
    private Optional<Serializer<T>> serializer;

    @Override
    public FileRepository<T> buildRepository() throws InvalidParameterException {
        try {
            return new FileRepository<>(filePath.get(), parser.get(), serializer.get());
        }catch(NoSuchElementException e) {
            throw new InvalidParameterException("FilePath, Parser or Serializer are null");
        }
    }

    public void setFilePath(String _filePath) {
        filePath = Optional.of(_filePath);
    }

    public void setParser(Parser<T> _parser) {
        parser = Optional.of(_parser);
    }

    public void setSerializer(Serializer<T> _serializer) {
        serializer = Optional.of(_serializer);
    }
}
