package sample.ui;

import com.google.inject.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.ApplicationConfig;

import java.io.IOException;

/**
 * Created by noex_ on 29.10.2016.
 */
public class GUIConfig extends AbstractModule {

    @Override
    protected void configure() {
        install(new ApplicationConfig());
        bind(ControllerError.class).toProvider(ControllerErrorProvider.class);
    }


    /**
     * Provides the {@link ControllerError}.
     * This Controller has it's own stage.
     */
    @Singleton
    static class ControllerErrorProvider implements Provider<ControllerError> {

        @Inject
        FXMLLoader fxmlLoader;

        @Inject
        Injector injector;

        @Override
        public ControllerError get() {
            ControllerError controllerError = null;
            Parent node = null;
            try {
                // The fxml can't load the same fxml twice with the same instance but setRoot is a workaround
                // http://stackoverflow.com/questions/21424843/exception-has-occuredroot-value-already-specified-in-javafx-when-loading-fxml-p
                fxmlLoader.setRoot(null);
                fxmlLoader.setController(null);
                fxmlLoader.setLocation( getClass().getResource(Resources.getErrorFXML()) );
                // Disable the controller factory since the reference to the controller in the fxml would be a circular dependency.
                fxmlLoader.setControllerFactory(null);
                node = fxmlLoader.load();
                controllerError = fxmlLoader.getController();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if( null == controllerError) {
                throw new IllegalStateException("Failed to create the ControllerError!.");
            }
            if( null == node ) {
                throw new IllegalStateException("Failed to load the fxml for the controller! Check the path is valid");
            }
            // Inject eventual other dependencies
            injector.injectMembers(controllerError);
            Stage stage = new Stage();
            stage.setScene(new Scene(node));
            controllerError.setStage(stage);

            return controllerError;
        }
    }


}
