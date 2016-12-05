package com.company.Utils.Builders;

import com.company.Domain.FisaPostElemDTO;
import com.company.Domain.Post;
import com.company.Domain.Sarcina;
import com.company.Repository.FileRepository;
import com.company.Utils.Builders.RepositoryBuilder.FileRepositoryBuilder;
import com.company.Utils.Parser;
import com.company.Utils.Serializer;
import com.sun.istack.internal.NotNull;

/**
 * Created by AlexandruD on 12/5/2016.
 */
public class FileRepos {

    private FileRepositoryBuilder<Sarcina> sarcinaBuilder;
    private FileRepositoryBuilder<Post> postBuilder;
    private FileRepositoryBuilder<FisaPostElemDTO> fisaPostBuilder;

    public FileRepos() {
        sarcinaBuilder = new FileRepositoryBuilder<>();
        postBuilder = new FileRepositoryBuilder<>();
        fisaPostBuilder = new FileRepositoryBuilder<>();
    }

    public FileRepository<Sarcina> newSarcinaFileRepo(
            @NotNull String filePath,
            @NotNull Parser<Sarcina> parser,
            @NotNull Serializer<Sarcina> serializer) {

        sarcinaBuilder.setFilePath(filePath);
        sarcinaBuilder.setParser(parser);
        sarcinaBuilder.setSerializer(serializer);
        return sarcinaBuilder.buildRepository();
    }

    public FileRepository<Post> newPostFileRepo(
            @NotNull  String filePath,
            @NotNull Parser<Post> parser,
            @NotNull Serializer<Post> serializer) {

        postBuilder.setFilePath(filePath);
        postBuilder.setSerializer(serializer);
        postBuilder.setParser(parser);

        return postBuilder.buildRepository();
    }

    public FileRepository<FisaPostElemDTO> newFisaPostRepo(
            @NotNull String filePath,
            @NotNull Parser<FisaPostElemDTO> parser,
            @NotNull Serializer<FisaPostElemDTO> serializer) {

        fisaPostBuilder.setParser(parser);
        fisaPostBuilder.setSerializer(serializer);
        fisaPostBuilder.setFilePath(filePath);

        return fisaPostBuilder.buildRepository();
    }

}
