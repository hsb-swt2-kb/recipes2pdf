package sample.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class GUI extends Application {

    private final String wrongPathException = "The path of the FXML-file is wrong or there are other problems with the loader.";
    private final String defaultFXML = "/sample/ui/Default.fxml";
    private final String pathIcon = "/sample/ui/icon_bg_small.png";
    private final String manageCookBookFXML = "/sample/ui/ManageCookBook.fxml";


    @Override
    public void start(Stage primaryStage) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(defaultFXML));
        final ResourceBundle locale_bundle = ResourceBundle.getBundle("bundles/locale", Locale.getDefault());
        loader.setResources(locale_bundle);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(wrongPathException);
        }
        sample.ui.ControllerDefault ctrl = loader.getController();

        primaryStage.setTitle("Kochbuchmanager");
        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream(pathIcon)));
        primaryStage.setScene(new Scene(root, 575, 390));
        primaryStage.setResizable(true);
        primaryStage.show();

        //Sets the default Pane in the default Controller
        ctrl.changeLayout(manageCookBookFXML);

    }

    public void start() {
        launch();
    }
}

