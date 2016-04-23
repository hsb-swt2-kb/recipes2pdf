package sample.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/GUI/Default.fxml"));
        Parent root = (Parent) loader.load();
        ControllerDefault ctrl = loader.getController();

        primaryStage.setTitle("Recipes2PDF");
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

