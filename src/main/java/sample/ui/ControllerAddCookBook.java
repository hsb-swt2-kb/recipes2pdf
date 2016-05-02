package sample.ui;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.*;
import javafx.stage.Stage;

public class ControllerAddCookBook {

        @FXML
        private Button closeButton;


    @FXML
    private Button browseButton;

        @FXML
        void closeAddCookBook(ActionEvent event) {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();

        }

    @FXML
    void openFileChooser(ActionEvent event) {
        FileHandler fileHandler = new FileHandler();
        fileHandler.importFiles();

    }

    }


