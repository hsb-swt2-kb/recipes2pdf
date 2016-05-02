package sample.ui;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXML;
/**
 * Created by Tobias on 30.04.2016.
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
