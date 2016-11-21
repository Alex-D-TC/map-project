package com.company.Service;

import com.company.Domain.*;
import com.company.Repository.CrudRepository;
import com.company.Utils.Exceptions.ElementExistsException;
import com.company.Utils.Exceptions.ElementNotFoundException;
import javafx.beans.*;

import javax.xml.bind.ValidationException;
import java.util.*;
import java.util.Observable;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by AlexandruD on 11/9/2016.
 */
public class FisaPostService extends CrudService<FisaPostElemDTO> {

    private PostService postController;
    private SarcinaService sarcinaController;

    public FisaPostService(CrudRepository<FisaPostElemDTO> repo, Validator<FisaPostElemDTO> validator,
                           PostService postController, SarcinaService sarcinaController) {
        super(repo, validator);
        this.postController = postController;
        this.sarcinaController = sarcinaController;
    }

    @Override
    public void add(FisaPostElemDTO fisa) throws ValidationException, ElementExistsException {

        String errors = checkConstraints(fisa.getPostID(), fisa.getSarcinaID());

        if(errors.compareTo("") != 0) {
            throw new ValidationException(errors);
        }

        super.add(fisa);
    }

    @Override
    public void update(FisaPostElemDTO oldFisa, FisaPostElemDTO newFisa) throws ValidationException, ElementNotFoundException {

        String errors = checkConstraints(newFisa.getPostID(), newFisa.getSarcinaID());

        if(errors.compareTo("") != 0) {
            throw new ElementNotFoundException(errors);
        }

        try {

            super.update(oldFisa, newFisa);

        } catch(NoSuchElementException e) {
            throw new ElementNotFoundException("No JD element found for the given id");
        }
    }

    public Iterable<Post> getPositions(Predicate<FisaPostElemDTO> pred) {
        return StreamSupport.stream(get(pred).spliterator(), false)
                .map((elem) -> {
                    int id = elem.getPostID();
                    return
                            StreamSupport.stream(postController.get((post) -> (post.getId() == id))
                                    .spliterator(), false)
                                    .reduce((a, b) -> (b))
                                    .get();
                })
                .collect(Collectors.toList());

    }

    public Iterable<Sarcina> getTasks(Predicate<FisaPostElemDTO> pred) {

        return StreamSupport.stream(get(pred).spliterator(), false)
                .map((elem) -> {
                    int id = elem.getSarcinaID();
                    return
                            StreamSupport.stream(sarcinaController.get((sarcina) -> (sarcina.getId() == id))
                                    .spliterator(), false)
                                    .reduce((a, b) -> (b))
                                    .get();
                })
                .collect(Collectors.toList());

    }

    public List<Sarcina> topTasks() {

        List<Map.Entry<Integer, Long>> apparitionList = new ArrayList<>();

        List<Integer> idsList =
                StreamSupport.stream(getAll().spliterator(), false)
                        .map((elem) -> (elem.getSarcinaID()))
                        .distinct()
                        .collect(Collectors.toList());

        idsList.forEach((id) -> {

            long appCount =
                    StreamSupport.stream(get((elem) -> (elem.getSarcinaID() == id))
                            .spliterator(), false)
                            .count();

            apparitionList.add(new AbstractMap.SimpleEntry<>(id, appCount));
        });

        Collections.sort(apparitionList, (a, b) -> (-1 * a.getValue().compareTo(b.getValue())));

        return apparitionList.stream()
                .limit(3)
                .map((id) -> (
                        StreamSupport.stream(sarcinaController.get((elem) -> (elem.getId() == id.getKey()))
                                .spliterator(), false)
                        .reduce((a, b) -> (b)).get()))
                .collect(Collectors.toList());

    }

    private boolean checkIfPostExists(int p) {
        return StreamSupport.stream(postController.get((elem) -> (p == elem.getId())).spliterator(), false)
                .count() == 1;
    }

    private boolean checkIfSarcinaExists(int s) {
        return StreamSupport.stream(sarcinaController.get((elem) -> (s == elem.getId())).spliterator(), false)
                .count() == 1;
    }

    private String checkConstraints(int p, int s) {
        StringBuilder builder = new StringBuilder();

        if(!checkIfPostExists(p))
            builder.append("Position id not found\n");

        if(!checkIfSarcinaExists(s))
            builder.append("Task id not found");

        return builder.toString();
    }

}
