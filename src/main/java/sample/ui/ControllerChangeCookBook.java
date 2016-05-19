package sample.ui;

/**
 * @author Tobias Stelter
 */


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.File;

public class ControllerChangeCookBook {

    @FXML
    private Button fileChooserButton;

        @FXML
        private Button closeButton;

    @FXML
    private TextField textFieldPicture;

    @FXML
    private Button changeButton;

    @FXML
    void changeCookBook(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

    }

        @FXML
        void closeChangeCookBook(ActionEvent event) {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();

        }

    @FXML
    void openFileChooser(ActionEvent event) {
        FileHandler fileHandler = new FileHandler();
        File file = fileHandler.importFile();
        if(file != null) {
            textFieldPicture.setText(file.getAbsolutePath());
        }

    }

    }


