package com.company.Utils.Commands;

/**
 * Created by AlexandruD on 10/14/2016.
 */
public class QuitCommand extends Command {

    public QuitCommand() {
        super("Quit", "Quits the program", () -> {
            System.out.println("Quitting...");
            System.exit(0);
            return "Why is this sh*t visible???";
        });
    }

}
