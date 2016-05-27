package sample.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * @author Tobias Stelter
 * The Class ''ControllerNotEnoughArguments'' manages the NotEnoughArguments-FXML.
 * It displays the notEnoughArguments-dialog.
 */

public class ControllerNotEnoughArguments {
    @FXML
    private Button closeButton;

    /**
     * The method ''closeDialog()'' closes the notEnoughArguments-dialog after a interaction with the close-button.
     *
     * @param event
     */

    @FXML
    void closeDialog(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();



    }
}
