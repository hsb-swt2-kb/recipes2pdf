package sample.guix;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class ControllerDefault {

    private final String wrongPathException = "The FXML-file is wrong or the FXML-file does not exist.";

    @FXML
    private MenuItem endMenuItem;

    @FXML
    private MenuItem addCookBook;

    @FXML
    private MenuItem addReceipe;

    @FXML
    private MenuItem manageCookBook;

    @FXML
    private MenuItem openDataFromRecipe;

    @FXML
    private MenuItem openDataFromCookBook;

    @FXML
    private MenuItem openHelp;

    @FXML
    private AnchorPane content;

    @FXML
    private MenuItem openAbout;

    void changeLayout(String fxml) {
        //Pane (Content) durch anderes Pane in anderer FXML ersetzten
        Parent newContent = null;
        try {
            newContent = FXMLLoader.load(getClass().getResource(fxml));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(wrongPathException);
        }

        //Set Constrains Back to the defaults
        content.setTopAnchor(newContent, 25.0);
        content.setBottomAnchor(newContent, 2.0);
        content.setRightAnchor(newContent, 0.0);
        content.setLeftAnchor(newContent, 0.0);
        content.getChildren().setAll(newContent);
    }

    void newWindow(String fxml) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(wrongPathException);
        }
        Stage stage = new Stage();
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream("/sample/GUI/icon_bg_small.png")));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void addCookBook(ActionEvent event) {
        //Pane (Content) durch anderes Pane in anderer FXML ersetzten
        changeLayout("/sample/GUI/AddCookBook.fxml");
    }

    @FXML
    void endProgramm(ActionEvent event) {
        System.exit(1);
    }

    @FXML
    void manageCookBook(ActionEvent event) {
        changeLayout("/sample/GUI/ManageCookBook.fxml");
    }

    @FXML
    void addReceipe(ActionEvent event) {
        //Pane (Content) durch anderes Pane in anderer FXML ersetzten
        newWindow("/sample/GUI/AddRecipe.fxml");
        changeLayout("/sample/GUI/ManageCookBook.fxml");
    }

    @FXML
    void openHelp(ActionEvent event) {
        newWindow("/sample/GUI/Help.fxml");

    }

    @FXML
    void openAbout(ActionEvent event) {
        newWindow("/sample/GUI/About.fxml");
    }

    @FXML
    void openDataFromRecipe(ActionEvent event) {

    }

    @FXML
    void openDataFromCookBook(ActionEvent event) {

    }

}







