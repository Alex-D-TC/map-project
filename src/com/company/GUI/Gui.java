package com.company.GUI;

import com.company.Utils.Commands.Command;
import com.company.Utils.IOUtils;
import com.company.Utils.ReadUtils;

import java.util.Map;

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
        String option;

        while(true) {

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

        StringBuilder result = new StringBuilder();

        commandMap.values().stream()
                            .map((comm) -> comm.getName() + " : " + comm.getDescription() + '\n')
                            .sorted()
                            .forEach(result::append);

        System.out.println(result.toString());
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
