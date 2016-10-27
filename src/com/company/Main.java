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
import com.company.Repository.CrudRepository;
import com.company.Tests.TestRunner;
import com.company.Utils.Commands.*;
import com.company.Utils.Exceptions.FailedTestException;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by AlexandruD on 10/8/2016.
 */
public class Main {

    private static PostController postController;
    private static SarcinaController sarcinaController;
    private static CrudRepository<Sarcina> sarcinaRepository;
    private static CrudRepository<Post> postRepository;

    private static final String USAGE = "USAGE: -[F|M] [taskFilePath, postFilePath]";
    private static boolean isFile;
    private static String taskFilePath, postFilePath;

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
            if(args.size() <= 2) {
                throw new InvalidArgumentException(new String[]{USAGE, "File paths must be passed if -F is chosen"});
            }
            taskFilePath = args.get(1);
            postFilePath = args.get(2);
        }
    }

    private static Map<String, Command> buildCommandMap(PostController postController,
                                                        SarcinaController sarcinaController) {

        List<Command> commandsList = new ArrayList<>(
                Stream.of(
                        new QuitCommand(),
                        new AddPost(postController),
                        new AddSarcina(sarcinaController),
                        new GetPosturi(postController),
                        new GetSarcini(sarcinaController),
                        new RemovePost(postController),
                        new RemoveSarcina(sarcinaController),
                        new UpdatePost(postController),
                        new UpdateSarcina(sarcinaController)
                ).collect(Collectors.toList()));

        return commandsList.stream().collect(Collectors.toMap(
                        (Command command) -> (command.getName()),
                        (Command command) -> (command)));
    }

    private static void buildRepositories() {
      if(isFile) {

            sarcinaRepository = new FileRepository<>(taskFilePath, (line) -> {
                Sarcina s = null;

                String[] tokens = line.split("[|]");
                int id = Integer.parseInt(tokens[0]);
                String description = tokens[1];

                s = new Sarcina(id, description);

                return s;
            }, (elem) -> elem.getId() + "|" + elem.getDescription());

            postRepository = new FileRepository<>(postFilePath, (line) -> {

                Post p = null;

                String[] tokens = line.split("[|]");
                int id = Integer.parseInt(tokens[0]);
                String name = tokens[1];
                Post.Type type = Post.stringToType(tokens[2]);

                p = new Post(id, name, type);

                return p;

            }, (elem) -> elem.getId() + "|" + elem.getName() + "|" + Post.typeToString(elem.getType()));

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

        TestRunner runner = new TestRunner();
        try {
            runner.run();
        }catch(FailedTestException e) {
            System.err.println(e.getMessage());
            return;
        }

        buildRepositories();
        buildControllers();

        Map<String, Command> commandMap = buildCommandMap(postController, sarcinaController);

        Gui g = new Gui(commandMap);
        g.start();
    }

}
