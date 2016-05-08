package sample.ui;

/**
 * @author Tobias Stelter
 */


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControllerAddCookBook {



    @FXML
        private Button closeButton;

    @FXML
    private Button generateButton;


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
        fileHandler.importFolder();

    }
    @FXML
    void generateCookBook(ActionEvent event) {
        //
        Stage stage = (Stage) generateButton.getScene().getWindow();
        stage.close();

    }

    }


