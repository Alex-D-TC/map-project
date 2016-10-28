package com.company.Domain;

/**
 * Created by AlexandruD on 10/14/2016.
 */
public class PostValidator extends Validator<Post> {

    @Override
    public boolean validate(Post p) {
        // Don't have much to validate bo$$
        if(p.getId() < 0)
            return false;
        return true;
    }

}
