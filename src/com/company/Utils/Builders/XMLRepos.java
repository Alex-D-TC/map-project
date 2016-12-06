package com.company.Utils.Builders;

import com.company.Domain.FisaPostElemDTO;
import com.company.Domain.Post;
import com.company.Domain.Sarcina;
import com.company.Repository.XMLRepository;
import com.company.Utils.Builders.RepositoryBuilder.XMLRepositoryBuilder;
import com.company.Utils.IO.XML.XMLParser;
import com.company.Utils.IO.XML.XMLSerializer;
import com.sun.istack.internal.NotNull;

import java.nio.file.Path;

/**
 * Created by AlexandruD on 12/6/2016.
 */
public class XMLRepos {

    private XMLRepositoryBuilder<Sarcina> sarcinaBuilder;
    private XMLRepositoryBuilder<Post> postBuilder;
    private XMLRepositoryBuilder<FisaPostElemDTO> fisaPostBuilder;

    public XMLRepos() {
        sarcinaBuilder = new XMLRepositoryBuilder<>();
        postBuilder = new XMLRepositoryBuilder<>();
        fisaPostBuilder = new XMLRepositoryBuilder<>();
    }

    public XMLRepository<Sarcina> newSarcinaXMLRepo(
            @NotNull Path filePath,
            @NotNull XMLParser<Sarcina> parser,
            @NotNull XMLSerializer<Sarcina> serializer) {

        sarcinaBuilder.setFilePath(filePath);
        sarcinaBuilder.setParser(parser);
        sarcinaBuilder.setSerializer(serializer);
        return sarcinaBuilder.buildRepository();
    }

    public XMLRepository<Post> newPostXMLRepo(
            @NotNull Path filePath,
            @NotNull XMLParser<Post> parser,
            @NotNull XMLSerializer<Post> serializer) {

        postBuilder.setFilePath(filePath);
        postBuilder.setSerializer(serializer);
        postBuilder.setParser(parser);

        return postBuilder.buildRepository();
    }

    public XMLRepository<FisaPostElemDTO> newFisaPostXMLRepo(
            @NotNull Path filePath,
            @NotNull XMLParser<FisaPostElemDTO> parser,
            @NotNull XMLSerializer<FisaPostElemDTO> serializer) {

        fisaPostBuilder.setParser(parser);
        fisaPostBuilder.setSerializer(serializer);
        fisaPostBuilder.setFilePath(filePath);

        return fisaPostBuilder.buildRepository();
    }

}
