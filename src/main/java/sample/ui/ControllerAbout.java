package sample.ui;

/**
 * @author Tobias Stelter
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControllerAbout {

    @FXML
    private Button closeButton;

    @FXML

    public void initialize() {


    }


    @FXML
    void closeAbout(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

    }

}
