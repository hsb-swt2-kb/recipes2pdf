package sample.ui;

/**
 * Created by Tobias on 22.04.2016.
 */

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.*;
import javafx.stage.Stage;

public class ControllerAbout {

    @FXML
    private Button closeButton;

    @FXML
    void closeAbout(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

    }

}
