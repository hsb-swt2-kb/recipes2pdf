package sample.ui;

/**
 * Created by Tobias on 30.04.2016.
 */
import javafx.fxml.FXML;
import javafx.event.*;
import javafx.scene.control.Button;

public class ControllerLoadRecipe {

    @FXML
    private Button fileChooserButton;

    @FXML
    void openFileChooser(ActionEvent event) {
       FileHandler fileHandler = new FileHandler();
        fileHandler.importFiles();

    }

}
