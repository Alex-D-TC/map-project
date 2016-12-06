package com.company;

/**
 * Created by AlexandruD on 11/20/2016.
 */

import com.company.Controller.SarcinaController;
import com.company.Service.CrudService;
import com.company.Service.ObservableCrudService;
import com.company.Service.SarcinaService;
import com.company.Utils.AppContext;
import com.company.View.SarcinaView;
import com.company.View.View;
import com.sun.javaws.exceptions.InvalidArgumentException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

public class App extends Application {

    private static AppContext app_context;
    private static final String ROOT_PATH = "com.company";

    public static void main(String[] args) {
        app_context = new AppContext();
        try {
            app_context.parseArguments(Arrays.asList(args));
            app_context.build();
            launch(args);
        }catch(InvalidArgumentException e) {
            System.err.println(app_context.USAGE);
            System.exit(1);
        }
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
     *      Note the use of capitalization! The context name must be passed in camelcase, with the first letter lowercase<br><br>
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

            // contextView, contextController, app_context.getContextService()

            Method getter = app_context.getClass().getDeclaredMethod("get"+capitalizedContext+"Service");

            Class controllerClass = Class.forName(ROOT_PATH+".Controller."+capitalizedContext+"Controller");

            final String SERVICE_CLASSPATH = "com.company.Service.";

            Class serviceClass = Class.forName(SERVICE_CLASSPATH + capitalizedContext + "Service");

            Constructor controllerConstructor = controllerClass.getConstructor(serviceClass);

            CrudService service = (CrudService) getter.invoke(app_context);
            View view = loader.getController();

            view.setController(controllerConstructor.newInstance(
                    serviceClass.cast(service)));

            Scene mainScene = new Scene(root);

            return Optional.of(mainScene);

        }catch(IOException |
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
        Scene fisaPostScene = buildScene("job_descript.fxml", "fisaPost").get();

        primaryStage.setTitle("Positions");
        primaryStage.setResizable(false);
        primaryStage.setScene(positionsScene);
        primaryStage.show();

        Stage taskStage = new Stage();

        taskStage.setTitle("Tasks");
        taskStage.setResizable(false);

        SarcinaView taskView = new SarcinaView(new SarcinaController(
                (SarcinaService)app_context.getSarcinaService()));

        taskStage.setScene(new Scene(taskView.getView()));
        taskStage.show();

        Stage fisaPostStage = new Stage();
        fisaPostStage.setTitle("Job description");
        fisaPostStage.setResizable(false);
        fisaPostStage.setScene(fisaPostScene);

        fisaPostStage.show();
    }
}
