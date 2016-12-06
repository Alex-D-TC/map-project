package com.company.Domain;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by AlexandruD on 10/8/2016.
 */
public class Post implements Serializable {

    private int id;
    private String name;
    private Type type;

    public enum Type implements Serializable {
        FULLTIME,
        PARTTIME
    }

    public static final String FULL_TIME_STRING = "FullTime";
    public static final String PART_TIME_STRING = "PartTime";

    public static Map<String, Type> typeMap = Collections.unmodifiableMap(
          Stream.of(
            new AbstractMap.SimpleEntry<>(FULL_TIME_STRING, Type.FULLTIME),
            new AbstractMap.SimpleEntry<>(PART_TIME_STRING, Type.PARTTIME)
          ).collect(Collectors.toMap((e) -> (e.getKey()), (e) -> (e.getValue()))));

    public Post() {
        // :>
    }

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
                return FULL_TIME_STRING;
            default:
                return PART_TIME_STRING;
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
