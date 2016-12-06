package com.company.Utils;

import com.company.Domain.*;
import com.company.Repository.CrudRepository;
import com.company.Repository.InMemoryRepository;
import com.company.Service.ObservableCrudService;
import com.company.Utils.Builders.FileRepos;
import com.company.Utils.Builders.Services;
import com.company.Utils.Builders.XMLRepos;
import com.company.Utils.Factories.ParserFactory.FileFisaPostParserFactory;
import com.company.Utils.Factories.ParserFactory.FilePostParserFactory;
import com.company.Utils.Factories.ParserFactory.FileSarcinaParserFactory;
import com.company.Utils.Factories.ParserFactory.FisaPostSerializerFactory;
import com.company.Utils.Factories.SerializerFactory.*;
import com.company.Utils.IO.XML.FisaPostXMLParser;
import com.company.Utils.IO.XML.PostXMLParser;
import com.company.Utils.IO.XML.SarcinaXMLParser;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by AlexandruD on 12/5/2016.
 */
public class AppContext {

    private ObservableCrudService<FisaPostElemDTO> fisaPostService;
    private ObservableCrudService<Post> postService;
    private ObservableCrudService<Sarcina> sarcinaService;
    private CrudRepository<FisaPostElemDTO> fisaPostRepository;
    private CrudRepository<Sarcina> sarcinaRepository;
    private CrudRepository<Post> postRepository;

    public static final String USAGE = "USAGE: -[F|M|X] [taskFilePath, postFilePath, jobDescriptFilePath]";
    private boolean isFile, isXML;
    private String taskFilePath, postFilePath, fisaPostFilePath;

    /**
     * Parses the command-line arguments
     * @param args - The args
     * @throws InvalidArgumentException
     */
    public void parseArguments(List<String> args) throws InvalidArgumentException {

        if(args.size() == 0) {
            throw new InvalidArgumentException(null);
        }

        String options =
                args.stream().filter((arg) -> (arg.matches("-.*"))).collect(Collectors.toList()).get(0);

        List<String> opts = new ArrayList<>();

        if(options.contains("F"))
            opts.add("F");

        if(options.contains("M"))
            opts.add("M");

        if(options.contains("X"))
            opts.add("X");

        if(opts.size() != 1) {
            throw new InvalidArgumentException(new String[]{"Invalid repository options"});
        }

        isFile = options.contains("F");
        isXML = options.contains("X");

        if(isFile || isXML) {
            if(args.size() <= 3) {
                throw new InvalidArgumentException(new String[]{USAGE, "File paths must be passed if -F is chosen"});
            }
            taskFilePath = args.get(1);
            postFilePath = args.get(2);
            fisaPostFilePath = args.get(3);
        }
    }


    /**
     * Constructs the controllers
     */
    public void buildServices(CrudRepository<Post> postRepository,
                                     CrudRepository<Sarcina> sarcinaRepository,
                                     CrudRepository<FisaPostElemDTO> fisaPostRepository) {

        Services services = new Services();

        postService = services.newObservablePostService(postRepository, new PostValidator());

        sarcinaService = services.newObservableSarcinaService(sarcinaRepository, new SarcinaValidator());

        fisaPostService = services.newObservableFisaPostService(
                fisaPostRepository, new FisaPostElemDTOValidator(), postService, sarcinaService);
    }

    /**
     * Constructs the repositories
     */
    public void buildRepositories() {
        if(isFile) {

            FileRepos fileRepos = new FileRepos();

            sarcinaRepository = fileRepos.newSarcinaFileRepo(
                    taskFilePath,
                    new FileSarcinaParserFactory().buildParser(),
                    new SarcinaSerializerFactory().buildSerializer());

            postRepository = fileRepos.newPostFileRepo(
                    postFilePath,
                    new FilePostParserFactory().buildParser(),
                    new PostSerializerFactory().buildSerializer());

            fisaPostRepository = fileRepos.newFisaPostRepo(
                    fisaPostFilePath,
                    new FileFisaPostParserFactory().buildParser(),
                    new FisaPostSerializerFactory().buildSerializer());


        } else if(isXML) {

            XMLRepos xmlRepos = new XMLRepos();

            sarcinaRepository = xmlRepos.newSarcinaXMLRepo(
                    Paths.get(taskFilePath),
                    new SarcinaXMLParser(),
                    new SarcinaXMLSerializerFactory().newSarcinaXMLSerializer());

            postRepository = xmlRepos.newPostXMLRepo(
                    Paths.get(postFilePath),
                    new PostXMLParser(),
                    new PostXMLSerializerFactory().newPostXMLSerializer());

            fisaPostRepository = xmlRepos.newFisaPostXMLRepo(
                    Paths.get(fisaPostFilePath),
                    new FisaPostXMLParser(),
                    new FisaPostElemXMLSerializerFactory().newFisaPostXMLSerializer());

        } else {
            sarcinaRepository = new InMemoryRepository<>();
            postRepository = new InMemoryRepository<>();
            fisaPostRepository = new InMemoryRepository<>();
        }

    }

    public void build() {
        buildRepositories();
        buildServices(postRepository, sarcinaRepository, fisaPostRepository);
    }

    public ObservableCrudService<Post> getPostService() {
        return postService;
    }

    public ObservableCrudService<Sarcina> getSarcinaService() {
        return sarcinaService;
    }

    public ObservableCrudService<FisaPostElemDTO> getFisaPostService() {
        return fisaPostService;
    }

}
