package sample.ui;

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


    private static ControllerError instance;
    @FXML
    private Label labelBoldPrint;
    @FXML
    private Label labelLittlePrint;
    @FXML
    private Button closeButton;

    public static ControllerError getInstance() {
        if (ControllerError.instance == null) {
            synchronized (ControllerError.class) {
                if (ControllerError.instance == null) {
                    ControllerError.instance = new ControllerError();
                }
            }
        }
        return ControllerError.instance;
    }

    @FXML
    private void initialize() {
        instance = this;
    }

    public void setLabels(String boldPrint, String littlePrint) {
        this.labelBoldPrint.setText(boldPrint);
        this.labelLittlePrint.setText(littlePrint);
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
