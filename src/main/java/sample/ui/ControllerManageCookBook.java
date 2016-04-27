package sample.ui;

/**
 * Created by Tobias on 24.04.2016.
 */

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.*;

public class ControllerManageCookBook {

    private final String exportFXML = "/sample/ui/ExportCookBook.fxml";
    private final String export = "Exportieren";
    private final String deleteFXML = "/sample/ui/DeleteDialog.fxml";
    private final String delete = "Löschen";

    ControllerDefault controllerDefault = new ControllerDefault();

    @FXML
    private Button delteButtonCookBook;

    @FXML
    private Button delteButtonRecipe;

    @FXML
    private Button export2pdfButton;

    @FXML
    void delteCookBook(ActionEvent event) {
        controllerDefault.newWindow(deleteFXML, delete);


    }

    @FXML
    void delteRecipe(ActionEvent event) {
        controllerDefault.newWindow(deleteFXML, delete);

    }

    @FXML
    void export2pdf(ActionEvent event) {
        controllerDefault.newWindow(exportFXML, export);

    }

}
