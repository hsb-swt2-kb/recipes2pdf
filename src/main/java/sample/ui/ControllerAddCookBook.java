package sample.ui;

/**
 * @author Tobias Stelter
 */


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;

public class ControllerAddCookBook {



    @FXML
    private Button closeButton;

    @FXML
    private TextField textFieldPicture;

    @FXML
    private TextField textFieldName;

    @FXML
    private TextArea textAreaVorwort;

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
        File file = fileHandler.importPicture();
        if(file != null) {
            textFieldPicture.setText(file.getAbsolutePath());
        }

    }
    @FXML
    void generateCookBook(ActionEvent event) {
        //
        Stage stage = (Stage) generateButton.getScene().getWindow();
        stage.close();

    }

    }


