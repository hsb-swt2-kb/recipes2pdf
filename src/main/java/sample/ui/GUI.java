package sample.ui;

/**
 * @author Tobias Stelter
 */

import com.gluonhq.ignite.guice.GuiceContext;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.ApplicationConfig;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;


//public class GUI extends Application implements UI{
public class GUI extends Application {

    @Inject FXMLLoader loader;
    private final String wrongPathException = "The path of the FXML-file is wrong or there are other problems with the loader.";

    private GuiceContext guiceContext;

    /**
     * @author Tobias Stelter
     * The Class ''GUI'' initializes the GUI.
     */
    public void start(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        guiceContext = new GuiceContext(this, () -> Arrays.asList(new GUIConfig()));
        guiceContext.init();

        final ResourceBundle locale_bundle = ResourceBundle.getBundle("bundles/locale", Locale.getDefault());
        loader.setResources(locale_bundle);
        loader.setLocation(getClass().getResource(Resources.getDefaultFXML()));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(wrongPathException);
        }
        sample.ui.ControllerDefault ctrl = loader.getController();

        primaryStage.setTitle("Kochbuchmanager");
        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream(Resources.getDefaultIcon())));
        primaryStage.setScene(new Scene(root, 600, 390));
        primaryStage.setResizable(true);
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(600);
        primaryStage.show();

        //Sets the default Pane in the default Controller
        ctrl.changeLayout(Resources.getMangeCookBookFXML());

    }

}

