package com.company.Utils.Builders;

import com.company.Domain.FisaPostElemDTO;
import com.company.Domain.Post;
import com.company.Domain.Sarcina;
import com.company.Domain.Validator;
import com.company.Repository.CrudRepository;
import com.company.Service.CrudService;
import com.company.Service.ObservableCrudService;
import com.company.Utils.Builders.ServiceBuilder.*;
import com.sun.istack.internal.NotNull;

/**
 * Created by AlexandruD on 12/5/2016.
 */
public class Services {

    private PostServiceBuilder postServiceBuilder;
    private SarcinaServiceBuilder sarcinaServiceBuilder;
    private FisaPostServiceBuilder fisaPostServiceBuilder;
    private ObservablePostServiceBuilder obsPostServiceBuilder;
    private ObservableSarcinaServiceBuilder obsSarcinaServiceBuilder;
    private ObservableFisaPostServiceBuilder obsFisaPostServiceBuilder;

    public Services() {
        postServiceBuilder = new PostServiceBuilder();
        sarcinaServiceBuilder = new SarcinaServiceBuilder();
        fisaPostServiceBuilder = new FisaPostServiceBuilder();
        obsFisaPostServiceBuilder = new ObservableFisaPostServiceBuilder();
        obsSarcinaServiceBuilder = new ObservableSarcinaServiceBuilder();
        obsPostServiceBuilder = new ObservablePostServiceBuilder();
    }

    public CrudService<Sarcina> newSarcinaService(
        @NotNull CrudRepository<Sarcina> repo,
        @NotNull Validator<Sarcina> validator) {

        sarcinaServiceBuilder.setValidator(validator);
        sarcinaServiceBuilder.setRepository(repo);

        return sarcinaServiceBuilder.buildService();
    }

    public ObservableCrudService<Sarcina> newObservableSarcinaService(
            @NotNull CrudRepository<Sarcina> repo,
            @NotNull Validator<Sarcina> validator) {

        obsSarcinaServiceBuilder.setValidator(validator);
        obsSarcinaServiceBuilder.setRepository(repo);

        return obsSarcinaServiceBuilder.buildService();
    }

    public CrudService<Post> newPostService(
        @NotNull CrudRepository<Post> repo,
        @NotNull Validator<Post> validator) {

        postServiceBuilder.setValidator(validator);
        postServiceBuilder.setRepository(repo);

        return postServiceBuilder.buildService();
    }

    public ObservableCrudService<Post> newObservablePostService(
            @NotNull CrudRepository<Post> repo,
            @NotNull Validator<Post> validator) {

        obsPostServiceBuilder.setValidator(validator);
        obsPostServiceBuilder.setRepository(repo);

        return obsPostServiceBuilder.buildService();
    }

    public CrudService<FisaPostElemDTO> newFisaPostService(
            @NotNull CrudRepository<FisaPostElemDTO> repo,
            @NotNull Validator<FisaPostElemDTO> validator,
            @NotNull ObservableCrudService<Post> postService,
            @NotNull ObservableCrudService<Sarcina> sarcinaService) {

        fisaPostServiceBuilder.setValidator(validator);
        fisaPostServiceBuilder.setRepository(repo);
        fisaPostServiceBuilder.setPostService(postService);
        fisaPostServiceBuilder.setSarcinaService(sarcinaService);

        return fisaPostServiceBuilder.buildService();
    }

    public ObservableCrudService<FisaPostElemDTO> newObservableFisaPostService(
            @NotNull CrudRepository<FisaPostElemDTO> repo,
            @NotNull Validator<FisaPostElemDTO> validator,
            @NotNull ObservableCrudService<Post> postService,
            @NotNull ObservableCrudService<Sarcina> sarcinaService) {

        obsFisaPostServiceBuilder.setValidator(validator);
        obsFisaPostServiceBuilder.setRepository(repo);
        obsFisaPostServiceBuilder.setPostService(postService);
        obsFisaPostServiceBuilder.setSarcinaService(sarcinaService);

        return obsFisaPostServiceBuilder.buildService();
    }

}
