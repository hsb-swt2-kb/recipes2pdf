package sample.ui;

/**
 * Created by Tobias on 28.04.2016.
 */

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.*;

public class ControllerManageCookBooks {


    private final String changeCookBookFXML = "/sample/ui/ChangeCookBook.fxml";
    private final String changeCookBookText = "Kochbuch ändern";

    @FXML
    private Button changeButton;

    @FXML
    void changeCookBook(ActionEvent event) {
        ControllerDefault controllerDefault = new ControllerDefault();
        controllerDefault.newWindow(changeCookBookFXML, changeCookBookText);

    }

}
