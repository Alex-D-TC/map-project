package com.company.Utils.Commands;

/**
 * Created by AlexandruD on 10/14/2016.
 */
public abstract class Command {

    private String name;
    private String description;
    private CommandMethod method;

    public Command(String name, String description, CommandMethod method) {
        this.name = name;
        this.description = description;
        this.method = method;
    }

    public String execute() {
        return method.doMethod();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
