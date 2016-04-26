package sample.gui;

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

    private final String wrongPathException = "The path of the FXML-file is wrong.";

    @Override
    public void start(Stage primaryStage) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/GUI/Default.fxml"));
        final ResourceBundle locale_bundle = ResourceBundle.getBundle("bundles/locale", Locale.getDefault());
        loader.setResources(locale_bundle);
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(wrongPathException);
        }
        sample.gui.ControllerDefault ctrl = loader.getController();

        primaryStage.setTitle("Recipes2PDF");
        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/sample/GUI/icon_bg_small.png" )));
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(true);
        primaryStage.show();

        //Sets the default Pane in the default Controller
        ctrl.changeLayout("/sample/GUI/AddCookBook.fxml");

    }

    public void start() {
        launch();
    }
}

