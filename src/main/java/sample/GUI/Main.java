package sample.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/GUI/Default.fxml"));
        final ResourceBundle locale_bundle = ResourceBundle.getBundle("bundles/locale", Locale.getDefault());
        loader.setResources(locale_bundle);
        Parent root = (Parent) loader.load();
        sample.GUI.ControllerDefault ctrl = loader.getController();

        primaryStage.setTitle("Recipes2PDF");
        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/sample/GUI/icon_bg_small.png" )));
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(true);
        primaryStage.show();

        //Sets the default Pane in the default Controller
        ctrl.changeLayout("/sample/GUI/AddCookBook.fxml");

    }

    public static void main(String[] args) throws Exception{
        launch(args);
    }
}

