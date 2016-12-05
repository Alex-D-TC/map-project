package com.company.Utils.Builders.ServiceBuilder;

import com.company.Domain.FisaPostElemDTO;
import com.company.Domain.Post;
import com.company.Domain.Sarcina;
import com.company.Service.FisaPostService;
import com.company.Service.ObservableCrudService;

import java.security.InvalidParameterException;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Created by AlexandruD on 12/5/2016.
 */
public class FisaPostServiceBuilder extends ServiceBuilder<FisaPostElemDTO> {

    private Optional<ObservableCrudService<Post>> postService;
    private Optional<ObservableCrudService<Sarcina>> sarcinaService;

    @Override
    public FisaPostService buildService() throws InvalidParameterException {
        try {
            return new FisaPostService(
                    repository.get(), validator.get(), postService.get(), sarcinaService.get());
        }catch(NoSuchElementException e) {
            throw new InvalidParameterException("Repository, validator, PostService or SarcinaService are null");
        }
    }

    public void setPostService(ObservableCrudService<Post> _postService) {
        postService = Optional.of(_postService);
    }

    public void setSarcinaService(ObservableCrudService<Sarcina> _sarcinaService) {
        sarcinaService = Optional.of(_sarcinaService);
    }

}
