package com.company.Domain;

import java.io.Serializable;

/**
 * Created by AlexandruD on 11/9/2016.
 */
public class FisaPostElemDTO implements Serializable {

    private int postID;
    private int sarcinaID;

    public FisaPostElemDTO() {
        postID = 0;
        sarcinaID = 0;
    }

    public FisaPostElemDTO(int postID, int sarcinaID) {
        this.postID = postID;
        this.sarcinaID = sarcinaID;
    }

    public int getPostID() {
        return postID;
    }

    public int getSarcinaID() {
        return sarcinaID;
    }

    public void setPostID(int id) {
        postID = id;
    }

    public void setSarcinaID(int id) {
        sarcinaID = id;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof FisaPostElemDTO))
            return false;

        FisaPostElemDTO oth = (FisaPostElemDTO) obj;

        return postID == oth.postID && sarcinaID == oth.sarcinaID;
    }
}
