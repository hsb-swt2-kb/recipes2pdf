package sample.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * @author Tobias Stelter
 * The Class ''ControllerNoElementSelectedDialog'' manages the NoElementSelectedDialog-FXML.
 * It displays the NoElementSelectedDialog-dialog.
 */

public class ControllerNoElementSelectedDialog {
    @FXML
    private Button closeButton;

    /**
     * The method ''closeDialog()'' closes the noElementSelectedDialo-dialog after a interaction with the close-button.
     *
     * @param event
     */

    @FXML
    void closeDialog(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();



    }
}
