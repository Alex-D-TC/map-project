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
public class FisaPostService extends ObservableCrudService<FisaPostElemDTO> {

    private ObservableCrudService<Post> postController;
    private ObservableCrudService<Sarcina> sarcinaController;

    public FisaPostService(CrudRepository<FisaPostElemDTO> repo,
                           Validator<FisaPostElemDTO> validator,
                           ObservableCrudService<Post> postController,
                           ObservableCrudService<Sarcina> sarcinaController) {
        super(repo, validator);
        this.postController = postController;
        this.sarcinaController = sarcinaController;

        // Remove all elements that have an invalid position id or task id
        List<Integer> positionIDs = StreamSupport.stream(postController.getAll().spliterator(), false)
                .map(Post::getId)
                .collect(Collectors.toList());

        List<Integer> taskIDs = StreamSupport.stream(sarcinaController.getAll().spliterator(), false)
                .map(Sarcina::getId)
                .collect(Collectors.toList());

        List<FisaPostElemDTO> toRemove = StreamSupport.stream(getAll().spliterator(), false)
                .filter((elem) -> !positionIDs.contains(elem.getPostID()) || !taskIDs.contains(elem.getSarcinaID()))
                .collect(Collectors.toList());

        toRemove.forEach((elem) -> {
            try {
                remove(elem);
            }catch(ElementNotFoundException e) {
                e.printStackTrace();
            }
        });
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

    /**
     * Returns the positions that match the condition imposed upon the position - task bind
     * @param pred The predicate representing the condition
     * @return The list of matching positions
     */
    public Iterable<Post> getPositionsOfBindings(Predicate<FisaPostElemDTO> pred) {
        return StreamSupport.stream(get(pred).spliterator(), false)
                .map((elem) -> {
                    int id = elem.getPostID();
                    return
                            StreamSupport.stream(postController.get((post) -> (post.getId() == id))
                                    .spliterator(), false)
                                    .reduce((a, b) -> (b))
                                    .get();
                })
                .distinct()
                .collect(Collectors.toList());

    }

    /**
     * Returns the tasks that match the condition imposed upon the position - task bind
     * @param pred The predicate representing the condition
     * @return The list of matching tasks
     */
    public Iterable<Sarcina> getTasksOfBindings(Predicate<FisaPostElemDTO> pred) {

        return StreamSupport.stream(get(pred).spliterator(), false)
                .map((elem) -> {
                    int id = elem.getSarcinaID();
                    return
                            StreamSupport.stream(sarcinaController.get((sarcina) -> (sarcina.getId() == id))
                                    .spliterator(), false)
                                    .reduce((a, b) -> (b))
                                    .get();
                })
                .distinct()
                .collect(Collectors.toList());

    }

    /**
     * Get the tasks sorted by the number of times they appear in the dataset.
     * The tasks returned must have appeared at least once
     * @return The top tasks
     */
    public List<AbstractMap.SimpleEntry<Sarcina, Long>> topTasks() {

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

            if(appCount != 0)
                apparitionList.add(new AbstractMap.SimpleEntry<>(id, appCount));
        });

        Collections.sort(apparitionList, (a, b) -> (-1 * a.getValue().compareTo(b.getValue())));

        return apparitionList.stream()
                .map((entry) -> {
                        try {
                            Sarcina s =
                                StreamSupport.stream(sarcinaController.get((elem) ->
                                            (elem.getId() == entry.getKey()))
                                        .spliterator(), false)
                                        .reduce((a, b) -> (b)).get();
                            return new AbstractMap.SimpleEntry<>(s, entry.getValue());
                        } catch (NoSuchElementException e) {
                            // In case the task was removed during execution
                            return null;
                        }
                    }
                )
                .filter((elem) -> elem != null)
                .collect(Collectors.toList());

    }

    /**
     * Add an invalidation listener to the underlying position controller
     * @param listener The listener
     */
    public void postControllerAddInvalidationListener(InvalidationListener listener) {
        postController.addListener(listener);
    }

    /**
     * Add an invalidation listener to the underlying task controller
     * @param listener The listener
     */
    public void sarcinaControllerAddInvalidationListener(InvalidationListener listener) {
        sarcinaController.addListener(listener);
    }

    public Iterable<Post> postControllerGetAll() {
        return postController.getAll();
    }

    public Iterable<Sarcina> sarcinaControllerGetAll() {
        return sarcinaController.getAll();
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
