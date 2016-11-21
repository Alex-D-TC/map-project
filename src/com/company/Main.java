package com.company;

// PROJECT THEME

// cs.ubbcluj.ro/~camelia/map
// Implement CRUD on an entity
// Validate input. Comment muh code. Test stuff
// PROBLEM 3

import com.company.Service.FisaPostService;
import com.company.Service.PostService;
import com.company.Service.SarcinaService;
import com.company.Domain.*;
import com.company.GUI.Gui;
import com.company.Repository.FileRepository;
import com.company.Repository.InMemoryRepository;
import com.company.Repository.CrudRepository;
import com.company.Utils.Commands.*;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by AlexandruD on 10/8/2016.
 */
public class Main {

    public static FisaPostService fisaPostService;
    public static PostService postService;
    public static SarcinaService sarcinaService;
    public static CrudRepository<FisaPostElemDTO> fisaPostRepository;
    public static CrudRepository<Sarcina> sarcinaRepository;
    public static CrudRepository<Post> postRepository;

    public static final String USAGE = "USAGE: -[F|M] [taskFilePath, postFilePath, jobDescriptFilePath]";
    public static boolean isFile;
    public static String taskFilePath, postFilePath, fisaPostFilePath;

    /**
     * Parses the command-line arguments
     * @param args - The args
     * @throws InvalidArgumentException
     */
    public static void parseArguments(List<String> args) throws InvalidArgumentException {

        if(args.size() == 0) {
            throw new InvalidArgumentException(null);
        }

        String options =
                args.stream().filter((arg) -> (arg.matches("-.*"))).collect(Collectors.toList()).get(0);

        if(options.contains("F") && options.contains("M")) {
            throw new InvalidArgumentException(null);
        }

        isFile = options.contains("F");

        if(isFile) {
            if(args.size() <= 3) {
                throw new InvalidArgumentException(new String[]{USAGE, "File paths must be passed if -F is chosen"});
            }
            taskFilePath = args.get(1);
            postFilePath = args.get(2);
            fisaPostFilePath = args.get(3);
        }
    }

    private static Map<String, Command> buildCommandMap(PostService postController,
                                                        SarcinaService sarcinaController,
                                                        FisaPostService fisaPostService) {

        List<Command> commandsList = new ArrayList<>(
                Stream.of(
                        new QuitCommand(),
                        new AddPost(postController),
                        new AddFisaPostElem(fisaPostService),
                        new AddSarcina(sarcinaController),
                        new GetPosturi(postController),
                        new GetSarcini(sarcinaController),
                        new GetFisaPost(fisaPostService),
                        new RemoveFisaPostElem(fisaPostService),
                        new RemovePost(postController),
                        new RemoveSarcina(sarcinaController),
                        new UpdatePost(postController),
                        new UpdateSarcina(sarcinaController),
                        new TopTasks(fisaPostService),
                        new GetTasksBySubstring(sarcinaController),
                        new GetTasksByID(sarcinaController),
                        new GetTasksSortedByID(sarcinaController),
                        new GetPositionsBySubstring(postController),
                        new GetPositionsByType(postController),
                        new GetPositionsSortedByID(postController)
                ).collect(Collectors.toList()));

        return commandsList.stream()
                            .collect(Collectors.toMap(
                            (Command command) -> (command.getName()),
                            (Command command) -> (command)));
    }

    /**
     * Constructs the repositories
     */
    public static void buildRepositories() {
      if(isFile) {

            sarcinaRepository = new FileRepository<>(taskFilePath, (line) -> {
                Sarcina s = null;

                String[] tokens = line.split("[|]");

                if(tokens.length != 2) {
                    return null;
                }

                int id;
                String description;

                try {
                    id = Integer.parseInt(tokens[0]);
                    description = tokens[1];
                } catch(NumberFormatException e) {
                    return null;
                }

                s = new Sarcina(id, description);

                return s;
            }, (elem) -> elem.getId() + "|" + elem.getDescription());

            postRepository = new FileRepository<>(postFilePath, (line) -> {

                Post p = null;

                String[] tokens = line.split("[|]");

                if(tokens.length != 3) {
                    return null;
                }

                int id;

                try {
                    id = Integer.parseInt(tokens[0]);
                }catch(NumberFormatException e) {
                    return null;
                }

                String name = tokens[1];
                Post.Type type = Post.stringToType(tokens[2]);

                if(type == null) {
                    return null;
                }

                p = new Post(id, name, type);

                return p;

            }, (elem) -> elem.getId() + "|" + elem.getName() + "|" + Post.typeToString(elem.getType()));

            fisaPostRepository = new FileRepository<>(fisaPostFilePath, (line) -> {
                String[] lineSplit = line.split("[|]");

                if(lineSplit.length != 2)
                    return null;

                try {

                    int postID = Integer.parseInt(lineSplit[0]),
                        taskID = Integer.parseInt(lineSplit[1]);

                        return new FisaPostElemDTO(postID, taskID);

                }catch(NumberFormatException e) {
                    return null;
                }


            }, (elem) -> elem.getPostID() + "|" + elem.getSarcinaID());

        } else {
            sarcinaRepository = new InMemoryRepository<>();
            postRepository = new InMemoryRepository<>();
            fisaPostRepository = new InMemoryRepository<>();
        }

    }

    /**
     * Constructs the controllers
     */
    public static void buildServices() {
        postService =
                new PostService(postRepository, new PostValidator());

        sarcinaService =
                new SarcinaService(sarcinaRepository, new SarcinaValidator());

        fisaPostService =
                new FisaPostService(fisaPostRepository, new FisaPostElemDTOValidator(),
                        postService, sarcinaService);

    }

    public static void prepMain(String[] args) {

        try {
            parseArguments(Arrays.asList(args));
        }catch(InvalidArgumentException e) {
            System.err.println(USAGE);
            System.exit(1);
        }

        /** Tests
         *
         TestRunner runner = new TestRunner();
         try {
         runner.run();
         }catch(FailedTestException e) {
         System.err.println(e.getMessage());
         return;
         }
         */


        buildRepositories();
        buildServices();
    }

    /**
     * I think it's a utility function...
     * @param args Arguments
     */
    public static void main(String[] args) {

        prepMain(args);
        
        Map<String, Command> commandMap = buildCommandMap(
                postService, sarcinaService, fisaPostService);

        Gui g = new Gui(commandMap);
        g.start();
    }

}
