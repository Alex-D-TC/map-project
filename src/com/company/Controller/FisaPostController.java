package com.company.Controller;

import com.company.Domain.FisaPostElemDTO;
import com.company.Domain.Post;
import com.company.Domain.Sarcina;
import com.company.Service.FisaPostService;
import com.company.Service.ObservableCrudService;
import com.company.Utils.Exceptions.ElementExistsException;
import com.company.Utils.Exceptions.ElementNotFoundException;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.collections.ObservableList;

import javax.xml.bind.ValidationException;
import java.util.AbstractMap;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by AlexandruD on 11/20/2016.
 */
public class FisaPostController {

    private FisaPostService service;
    private ObservableList<Post> positionsModel;
    private ObservableList<Sarcina> tasksModel;

    public FisaPostController(ObservableCrudService<FisaPostElemDTO> _service) {
        // Dangerous!!
        service = (FisaPostService) _service;

        positionsModel = new ObservableListWrapper<>(
            StreamSupport.stream(service.postControllerGetAll().spliterator(), false)
            .collect(Collectors.toList())
        );

        service.postControllerAddInvalidationListener((observable) ->
            positionsModel.setAll(
                    StreamSupport.stream(service.postControllerGetAll().spliterator(), false)
                    .collect(Collectors.toList()))
        );

        tasksModel = new ObservableListWrapper<>(
            StreamSupport.stream(service.sarcinaControllerGetAll().spliterator(), false)
            .collect(Collectors.toList())
        );

        service.sarcinaControllerAddInvalidationListener((observable) ->
            tasksModel.setAll(
                    StreamSupport.stream(service.sarcinaControllerGetAll().spliterator(), false)
                    .collect(Collectors.toList()))
        );

    }

    public ObservableList<Post> getPositionsModel() {
        return positionsModel;
    }

    public ObservableList<Sarcina> getTasksModel() {
        return tasksModel;
    }

    public void addFisaPostElem(int positionId, int taskId)
        throws ValidationException, ElementExistsException {
        service.add(new FisaPostElemDTO(positionId, taskId));
    }

    public void removeFisaPostElem(int positionId, int taskId)
        throws ElementNotFoundException {
        service.remove(new FisaPostElemDTO(positionId, taskId));
    }

    public void updateFisaPostElem(int positionId, int taskId, int newTaskId)
        throws ValidationException, ElementNotFoundException {
        service.update(new FisaPostElemDTO(positionId, taskId), new FisaPostElemDTO(positionId, newTaskId));
    }

    /**
     * Returns the top tasks
     * @return A list of the pot tasks
     */
    public ObservableList<AbstractMap.SimpleEntry<Sarcina, Long>> topTasks() {
        return new ObservableListWrapper<>(service.topTasks());
    }

    /**
     * Gets all tasks that are bound to a given position
     * @param positionId the position id
     * @return The list of tasks bound to a position
     */
    public ObservableList<Sarcina> getTasksOfPosition(int positionId) {
        return new ObservableListWrapper<>(
                StreamSupport.stream(service.getTasksOfBindings((elem) -> elem.getPostID() == positionId).spliterator(), false)
                .collect(Collectors.toList()));
    }

}
