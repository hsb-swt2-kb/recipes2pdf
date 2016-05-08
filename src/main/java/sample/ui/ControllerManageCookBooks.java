package sample.ui;

/**
 * @author Tobias Stelter
 */


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControllerManageCookBooks {


    private final String changeCookBookFXML = "/sample/ui/ChangeCookBook.fxml";
    private final String defaultIconPath = "/sample/ui/icon_bg_small.png";
    private final String changeCookBookText = "Kochbuch ändern";

    @FXML
    private Button closeButton;


    @FXML
    private Button changeButton;

    @FXML
    void changeCookBook(ActionEvent event) {
        ControllerDefault controllerDefault = new ControllerDefault();
        controllerDefault.newWindow(changeCookBookFXML, changeCookBookText, 370, 245, defaultIconPath);

    }

    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

    }

}
