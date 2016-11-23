package com.company;

/**
 * Created by AlexandruD on 11/20/2016.
 */

import com.company.Controller.SarcinaController;
import com.company.Service.CrudService;
import com.company.Service.ObservableCrudService;
import com.company.View.SarcinaView;
import com.company.View.View;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class App extends Application {

    private static final String ROOT_PATH = "com.company";

    public static void main(String[] args) {
        Main.prepMain(args);
        launch(args);
    }

    /**
     * Builds a scene from a given fxml path and a special context string<br><br>
     *
     * Pass the context name without any capital letters.<br><br>
     *
     * The project structure respects these invariants:<br><br>
     *
     *      For a given valid context Cont, we can obtain the following:<br><br>
     *
     *      The view class, located at com.company.View.ContView<br>
     *      The controller class, located at com.company.Controller.ContController<br>
     *      The service class, located at com.company.Service.ContService<br>
     *      The base entity, located at com.company.Domain.Cont<br>
     *      The entity's validator, located at com.company.Domain.ContValidator<br><br>
     *
     *      As a helper, in the Main class there is a public static field identified as Main.contService,
     *      from which we can retrieve a fully functioning Service for our given context<br><br>
     *
     *      Note the use of capitalization!<br><br>
     *
     *      Make sure the context you pass here follows these given guidelines.<br>
     *
     * @param fxml The fxml path
     * @param context The context string
     * @return An {@link Optional} containing the {@link Scene}
     */
    private Optional<Scene> buildScene(String fxml, String context) {
        try {

            FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml));
            StackPane root = loader.load();

            String capitalizedContext = Character.toUpperCase(context.charAt(0)) + context.substring(1);

            // contextView, contextController, Main.contextService (static field)

            Class controllerClass = Class.forName(ROOT_PATH+".Controller."+capitalizedContext+"Controller");

            Constructor controllerConstructor = controllerClass.getConstructor(ObservableCrudService.class);

            Field mainField = Main.class.getField(context+"Service");

            CrudService service = (CrudService) mainField.get(null);
            View view = loader.getController();

            view.setController(controllerConstructor.newInstance(service));

            Scene mainScene = new Scene(root);

            return Optional.of(mainScene);

        }catch(IOException |
                NoSuchFieldException |
                IllegalAccessException |
                NoSuchMethodException |
                InvocationTargetException |
                InstantiationException |
                ClassNotFoundException e) {

            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public void start(Stage primaryStage) {
        Scene positionsScene = buildScene("positions.fxml", "post").get();

        primaryStage.setTitle("Positions");
        primaryStage.setScene(positionsScene);
        primaryStage.show();


        Stage taskStage = new Stage();

        taskStage.setTitle("Tasks");

        SarcinaView taskView = new SarcinaView(new SarcinaController(Main.sarcinaService));

        taskStage.setScene(new Scene(taskView.getView()));
        taskStage.show();

    }
}
