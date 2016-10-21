package com.company.GUI;

import com.company.Utils.Commands.Command;
import com.company.Utils.IOUtils;
import com.company.Utils.ReadUtils;

import java.util.Map;
import java.util.Scanner;

/**
 * Created by AlexandruD on 10/8/2016.
 */
public class Gui {

    private Map<String, Command> commandMap;

    public Gui(
            Map<String, Command> commandMap) {
        this.commandMap = commandMap;
    }

    /**
     * Starts the 'gui'
     */
    public void start() {
        Scanner scanner = IOUtils.getScannerInstanceOnIn();
        String option;

        while(1 == 1) {

            Command c = null;

            printMenu();
            while(c == null) {
                option = ReadUtils.readString(IOUtils.getScannerInstanceOnIn(), "Input command name: ");
                c = commandMap.get(option);
                if(c == null) {
                    System.out.println("Invalid option");
                }
            }

            System.out.println(clrscr());
            System.out.println(c.execute());
        }
    }

    /**
     * Prints the menu. Naturally
     */
    private void printMenu() {
        System.out.println("\nCommands list:\n");
        for(Command c : commandMap.values()) {
            System.out.println(c.getName() + ": " + c.getDescription());
        }
        System.out.println("");
    }

    /**
     * Returns a string of \n characters in order to clear the console
     * @return - The clear screen string
     */
    private String clrscr() {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < 2; ++i) {
            builder.append('\n');
        }
        return builder.toString();
    }

}
