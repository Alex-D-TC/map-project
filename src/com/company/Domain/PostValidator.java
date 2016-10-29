package com.company.Domain;

/**
 * Created by AlexandruD on 10/14/2016.
 */
public class PostValidator extends Validator<Post> {

    @Override
    public boolean validate(Post p) {
        // Don't have much to validate bo$$
        return p.getId() < 0;
    }

}
