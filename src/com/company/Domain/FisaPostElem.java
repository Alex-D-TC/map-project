package com.company.Domain;

/**
 * Created by AlexandruD on 11/9/2016.
 */
public class FisaPostElem {

    private final int id;
    private final Post post;
    private final Sarcina sarcina;

    public FisaPostElem(int id, Post post, Sarcina sarcina) {
        this.id = id;
        this.post = post;
        this.sarcina = sarcina;
    }

    public int getId() {
        return id;
    }

    public Post getPost() {
        return post;
    }

    public Sarcina getSarcina() {
        return sarcina;
    }



    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof FisaPostElem))
            return false;

        FisaPostElem oth = (FisaPostElem) obj;

        return oth.id == id;
    }
}
