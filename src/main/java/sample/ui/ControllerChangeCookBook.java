package sample.ui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControllerChangeCookBook {

    @FXML
    private Button fileChooserButton;

        @FXML
        private Button closeButton;

        @FXML
        void closeChangeCookBook(ActionEvent event) {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();

        }

    @FXML
    void openFileChooser(ActionEvent event) {
        FileHandler fileHandler = new FileHandler();
        fileHandler.importFiles();

    }

    }


