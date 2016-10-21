package com.company;

// PROJECT THEME

// cs.ubbcluj.ro/~camelia/map
// Implement CRUD on an entity
// Validate input. Comment muh code. Test stuff
// PROBLEM 3

import com.company.Controller.PostController;
import com.company.Controller.SarcinaController;
import com.company.Domain.Post;
import com.company.Domain.PostValidator;
import com.company.Domain.Sarcina;
import com.company.Domain.SarcinaValidator;
import com.company.GUI.Gui;
import com.company.Repository.FileRepository;
import com.company.Repository.InMemoryRepository;
import com.company.Repository.Repository;
import com.company.Tests.Tests;
import com.company.Utils.Commands.*;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by AlexandruD on 10/8/2016.
 */
public class Main {

    private static PostController postController;
    private static SarcinaController sarcinaController;
    private static Repository<Sarcina> sarcinaRepository;
    private static Repository<Post> postRepository;

    private static final String USAGE = "USAGE: -[F|D] [filePath]";
    private static boolean isFile;
    private static String filePath;

    private static void parseArguments(List<String> args) throws InvalidArgumentException {

        if(args.size() == 0) {
            throw new InvalidArgumentException(null);
        }

        String options =
                args.stream().filter((arg) -> (arg.matches("-.*"))).collect(Collectors.toList()).get(0);

        if(options.contains("F") && options.contains("M")) {
            throw new InvalidArgumentException(null);
        }

        if(options.contains("F")) {
            isFile = true;
        } else {
            isFile = false;
        }

        if(isFile) {
            if(args.size() <= 1) {
                throw new InvalidArgumentException(new String[]{"filePath must be passed if -F is chosen"});
            }
            filePath = args.get(2);
        }
    }

    private static Map<String, Command> buildCommandMap(PostController postController,
                                                        SarcinaController sarcinaController) {

        List<Command> commandsList = new ArrayList<>();

        commandsList.add(new QuitCommand());
        commandsList.add(new AddPost(postController));
        commandsList.add(new AddSarcina(sarcinaController));
        commandsList.add(new GetPosturi(postController));
        commandsList.add(new GetSarcini(sarcinaController));
        commandsList.add(new RemovePost(postController));
        commandsList.add(new RemoveSarcina(sarcinaController));
        commandsList.add(new UpdatePost(postController));
        commandsList.add(new UpdateSarcina(sarcinaController));

        return commandsList.stream().collect(Collectors.toMap(
                        (Command command) -> (command.getName()),
                        (Command command) -> (command)));
    }

    private static void buildRepositories() {
      if(isFile) {

            sarcinaRepository = new FileRepository<>(filePath, (BufferedReader reader) -> {
                Sarcina s = null;

                try {
                    String[] tokens = reader.readLine().split(";");
                    int id = Integer.parseInt(tokens[0]);
                    String description = tokens[2];

                    s = new Sarcina(id, description);

                }catch(IOException e) {
                    e.printStackTrace();
                }

                return s;
            });

            postRepository = new FileRepository<>(filePath, (BufferedReader reader) -> {

                Post p = null;

                try {

                    String[] tokens = reader.readLine().split(";");
                    int id = Integer.parseInt(tokens[0]);
                    String name = tokens[2];
                    Post.Type type = Post.stringToType(tokens[3]);

                    p = new Post(id, name, type);

                }catch(IOException e) {
                    e.printStackTrace();
                }

                return p;

            });

        } else {
            sarcinaRepository = new InMemoryRepository<>();
            postRepository = new InMemoryRepository<>();
        }

    }

    private static void buildControllers() {
        postController =
                new PostController(postRepository, new PostValidator());

        sarcinaController =
                new SarcinaController(sarcinaRepository, new SarcinaValidator());

    }

    public static void main(String[] args) {

        try {
            parseArguments(Arrays.asList(args));
        }catch(InvalidArgumentException e) {
            System.err.println(USAGE);
            System.exit(1);
        }

        Tests t = new Tests();
        t.test();

        buildRepositories();
        buildControllers();

        Map<String, Command> commandMap = buildCommandMap(postController, sarcinaController);

        Gui g = new Gui(commandMap);
        g.start();
    }

}
