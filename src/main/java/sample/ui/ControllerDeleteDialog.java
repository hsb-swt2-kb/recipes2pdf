package sample.ui;

/**
 * @author Tobias Stelter
 * The Class ''ControllerDeleteDialog'' manages the DeleteDialog-FXML.
 * It displays the delete-dialog.
 */


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

    /**
     * The method ''closeDeleteDialog()'' closes the delete-dialog after a interaction with the close-button.
     *
     * @param event
     */
    @FXML
    void closeDeleteDialog(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
