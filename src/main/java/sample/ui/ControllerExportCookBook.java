package sample.ui;

/**
 * @author Tobias Stelter
 */


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class ControllerExportCookBook {

    @FXML
    private Button browseButton;

    @FXML
    private Button saveButton;

    @FXML
    private MenuButton menuButtonFormat;

    @FXML
    private MenuItem menuItemA5;

    @FXML
    private MenuItem menuItemA4;



    @FXML
    private Button closeButton;

    @FXML
    void chooseA4(ActionEvent event) {
        menuButtonFormat.setText("A4");
    }

    @FXML
    void chooseA5(ActionEvent event) {
        menuButtonFormat.setText("A5");
    }

    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

    }

    @FXML
    void saveCookBook(ActionEvent event){
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();

    }
}
