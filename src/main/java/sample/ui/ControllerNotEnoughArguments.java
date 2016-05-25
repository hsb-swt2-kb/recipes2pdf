package sample.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * @author Tobias Stelter
 */

public class ControllerNotEnoughArguments {
    @FXML
    private Button closeButton;

    @FXML
    void closeDialog(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();



    }
}
