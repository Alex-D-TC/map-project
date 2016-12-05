package com.company;

import com.company.Service.*;
import com.company.Domain.*;
import com.company.GUI.Gui;
import com.company.Utils.AppContext;
import com.company.Utils.Commands.*;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by AlexandruD on 10/8/2016.
 */
public class Main {

    private static AppContext context;

    private static Map<String, Command> buildCommandMap(ObservableCrudService<Post> postController,
                                                        ObservableCrudService<Sarcina> sarcinaController,
                                                        ObservableCrudService<FisaPostElemDTO> fisaPostService) {

        List<Command> commandsList = new ArrayList<>(
                Stream.of(
                        new QuitCommand(),
                        new AddPost((PostService) postController),
                        new AddFisaPostElem((FisaPostService) fisaPostService),
                        new AddSarcina((SarcinaService) sarcinaController),
                        new GetPosturi((PostService) postController),
                        new GetSarcini((SarcinaService) sarcinaController),
                        new GetFisaPost((FisaPostService) fisaPostService),
                        new RemoveFisaPostElem((FisaPostService) fisaPostService),
                        new RemovePost((PostService) postController),
                        new RemoveSarcina((SarcinaService) sarcinaController),
                        new UpdatePost((PostService) postController),
                        new UpdateSarcina((SarcinaService) sarcinaController),
                        new TopTasks((FisaPostService) fisaPostService),
                        new GetTasksBySubstring((SarcinaService) sarcinaController),
                        new GetTasksByID((SarcinaService) sarcinaController),
                        new GetTasksSortedByID((SarcinaService) sarcinaController),
                        new GetPositionsBySubstring((PostService) postController),
                        new GetPositionsByType((PostService) postController),
                        new GetPositionsSortedByID((PostService) postController)
                ).collect(Collectors.toList()));

        return commandsList.stream()
                            .collect(Collectors.toMap(
                            (Command command) -> (command.getName()),
                            (Command command) -> (command)));
    }

    /**
     * I think it's a utility function...
     * @param args Arguments
     */
    public static void main(String[] args) {

        context = new AppContext();

        try {
            context.parseArguments(Arrays.asList(args));
            context.build();
        }catch(InvalidArgumentException e) {
            System.err.println(context.USAGE);
            System.exit(1);
        }

        Map<String, Command> commandMap = buildCommandMap(
                context.getPostService(), context.getSarcinaService(), context.getFisaPostService());

        Gui g = new Gui(commandMap);
        g.start();
    }

}
