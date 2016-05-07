package sample.ui;

/**
 * Created by Tobias on 27.04.2016.
 */

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.*;
import javafx.stage.Stage;
public class ControllerDeleteDialog {

    @FXML
    private Button closeButton;

    @FXML
    private Button deleteButton;

    @FXML
    void deleteElement(ActionEvent event) {
        Stage stage = (Stage) deleteButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void closeDeleteDialog(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();



    }
}