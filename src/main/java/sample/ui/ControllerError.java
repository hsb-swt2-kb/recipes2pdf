package sample.ui;

import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * @author Tobias Stelter
 * The Class ''ControllerNotEnoughArguments'' manages the NotEnoughArguments-FXML.
 * It displays the notEnoughArguments-dialog.
 */

public class ControllerError {

    @FXML
    private Label labelBoldPrint;
    @FXML
    private Label labelLittlePrint;
    @FXML
    private Button closeButton;
    Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setLabels(String boldPrint, String littlePrint) {
        this.labelBoldPrint.setText(boldPrint);
        this.labelLittlePrint.setText(littlePrint);
        showDialog();
    }

    public void showDialog() {
        stage.show();
    }

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
