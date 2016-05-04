package sample.ui;

/**
 * Created by Tobias on 25.04.2016.
 */

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.*;
import javafx.stage.Stage;

public class ControllerExportCookBook {

    @FXML
    private Button browseButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button closeButton;

    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

    }

    @FXML
    void saveCookBook(ActionEvent event){
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();

    }
}
