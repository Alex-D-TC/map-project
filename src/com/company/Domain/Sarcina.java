package com.company.Domain;

import java.io.Serializable;

/**
 * Created by AlexandruD on 10/8/2016.
 */
public class Sarcina implements Serializable {

    private int id;
    private String description;

    public Sarcina(int id) {
        this.id = id;
        description = "";
    }

    public Sarcina(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int nId) {
        id = nId;
    }

    public void setDescription(String nDescription) {
        description = nDescription;
    }

    public String toString() {
        return id + " " + description;
    }

    @Override
    public boolean equals(Object other) {

        if(!(other instanceof Sarcina)) {
            return false;
        }

        Sarcina oth = (Sarcina) other;

        return this.getId() == oth.getId();
    }

}
