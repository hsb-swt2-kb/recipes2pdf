package sample.ui;

/**
 * Created by Tobias on 24.04.2016.
 */

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.*;

public class ControllerManageCookBook {

    @FXML
    private Button export2pdfButton;
    private final String exportFXML = "/sample/ui/ExportCookBook.fxml";
    private final String export = "Exportieren";

    @FXML
    void export2pdf(ActionEvent event) {
        ControllerDefault controllerDefault = new ControllerDefault();
        controllerDefault.newWindow(exportFXML, export);

    }

}
