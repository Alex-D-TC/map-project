package com.company.Utils.Commands;

import com.company.Service.FisaPostService;
import com.company.Domain.FisaPostElemDTO;
import com.company.Utils.Exceptions.ElementNotFoundException;
import com.company.Utils.IO.IOUtils;
import com.company.Utils.IO.ReadUtils;

import java.util.NoSuchElementException;

/**
 * Created by AlexandruD on 11/9/2016.
 */
public class RemoveFisaPostElem extends Command {

    public RemoveFisaPostElem(FisaPostService mediator) {

        super("Remove JD element", "Removes a job description element", () -> {

            try {

                int postID = ReadUtils.readInt(IOUtils.getScannerInstanceOnIn(), "Input position id: "),
                    taskID = ReadUtils.readInt(IOUtils.getScannerInstanceOnIn(), "Input task id: ");
                
                mediator.remove(new FisaPostElemDTO(postID, taskID));
                return "Element removed successfully. :>";

            }catch(ElementNotFoundException e) {
                return e.getMessage();
            }catch(NoSuchElementException e) {
                return "No element hsa been found with the given id";
            }

        });

    }

}
