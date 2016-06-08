package sample.ui;

/**
 * @author Tobias Stelter
 * The Class ''ControllerAbout'' manages the Close-About-FXML.
 * The About-FXML contains information about the developers.
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControllerAbout {

    @FXML
    private Button closeButton;

    /**
     * The method ''closeAbout()'' closes the About-Window.
     *
     * @param event
     */
    @FXML
    void closeAbout(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
