package com.company.Domain;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by AlexandruD on 10/8/2016.
 */
public class Post {

    private int id;
    private String name;
    private Type type;

    public enum Type {
        FULLTIME,
        PARTTIME
    }

    public static String fullTimeString = "FullTime";
    public static String partTimeString = "PartTime";

    public static Map<String, Type> typeMap = Collections.unmodifiableMap(
          Stream.of(
            new AbstractMap.SimpleEntry<>(fullTimeString, Type.FULLTIME),
            new AbstractMap.SimpleEntry<>(partTimeString, Type.PARTTIME)
          ).collect(Collectors.toMap((e) -> (e.getKey()), (e) -> (e.getValue()))));

    public Post(int id) {
        this.id = id;
        name = "";
        type = Type.PARTTIME;
    }

    public Post(int id, String name, Type type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public void setId(int nId) {
        id = nId;
    }

    public void setName(String nName) {
        name = nName;
    }

    public void setType(Type nType) {
        type = nType;
    }

    public boolean equals(Post other) {
        return id == other.id;
    }

    public String toString() {
        return id + " " + name + " " + typeToString(type);
    }

    /**
     * Gets the type associated with the type string
     * @param string The type string
     * @return The type, if the type string is valid. Null otherwise
     */
    public static Type stringToType(String string) {
        return typeMap.get(string);
    }

    /**
     * Gets the type string associated with the type
     * @param type The type
     * @return The type string associated with the type
     */
    public static String typeToString(Type type) {
        switch (type) {
            case FULLTIME:
                return fullTimeString;
            default:
                return partTimeString;
        }
    }

    @Override
    public boolean equals(Object other) {

        if(!(other instanceof Post)) {
            return false;
        }

        Post oth = (Post) other;

        return this.getId() == oth.getId();
    }

}
