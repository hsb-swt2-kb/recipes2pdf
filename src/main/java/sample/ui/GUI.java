package sample.ui;

/**
 * @author Tobias Stelter
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

//public class GUI extends Application implements UI{
public class GUI extends Application {

    // TODO: handle database connection more efficiently
    // TODO: statusBar for status messages and progressBars
    // TODO: keyComb F1 for help
    // TODO: keyComb ESC for cancel dialogs
    // TODO: view ingredients in a tableView
    // TODO: add preamble and title picture to database
    // TODO: pdfBuilder: insert title page and TOC

    /**
     * @author Tobias Stelter
     * The Class ''GUI'' initializes the GUI.
     */
    @Override
    public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(Resources.getDefaultFXML()));
        final ResourceBundle locale_bundle = ResourceBundle.getBundle("bundles/locale", Locale.getDefault());
        loader.setResources(locale_bundle);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            String wrongPathException = "The path of the FXML-file is wrong or there are other problems with the loader.";
            System.out.println(wrongPathException);
        }
        sample.ui.ControllerDefault ctrl = loader.getController();

        primaryStage.setTitle("Kochbuchmanager");
        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream(Resources.getDefaultIcon())));
        if (root != null) {
            primaryStage.setScene(new Scene(root, 600, 390));
        }
        primaryStage.setResizable(true);
        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(600);
        primaryStage.show();

        //Sets the default Pane in the default Controller
        ctrl.changeLayout(Resources.getMangeCookBookFXML());

    }

    public void start() {
        launch();
    }
}

