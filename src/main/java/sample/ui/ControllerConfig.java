package sample.ui;

/**
 * Created by Tobias on 18.05.2016.
 */

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.*;
import javafx.stage.Stage;

public class ControllerConfig {

    @FXML
    private Button closeButton;

    @FXML
    void closeConfig(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

    }

}
