package sample.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * @author Tobias Stelter
 * The Class ''ControllerHelp'' manages the Help-FXML.
 * The Help-FXML contains a helptext.
 */

public class ControllerHelp {
    @FXML
    private Button closeButton;

    /**
     * The method ''closeHelp()'' closes the Help-Window.
     *
     * @param event
     */
    @FXML
    void closeHelp(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

    }
}
