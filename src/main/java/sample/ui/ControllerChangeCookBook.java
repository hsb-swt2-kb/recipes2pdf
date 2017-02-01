package sample.ui;

/**
 * @author Tobias Stelter
 * The Class ''ControllerChangeCookBook'' manages the ChangeCookBook-FXML.
 * It displays the cookbook-data and provides methods for changing a cookbook.
 * The cookbook contains at least a name and a sortlevel - a foreword and a picture are optional.
 */


import com.github.vbauer.herald.annotation.Log;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.slf4j.Logger;
import sample.model.Cookbook;

import javax.inject.Inject;
import java.io.File;

public class ControllerChangeCookBook {

    @Log
    private Logger LOG;
    File file;
    @FXML
    private Button closeButton;
    @FXML
    private TextField textFieldName;
    @FXML
    private Button changeButton;
    @Inject
    private ControllerError controllerError;
    @Inject
    private UI ui;
    @Inject
    private ControllerManageCookBook controllerManageCookBook;
    @Inject
    private ControllerManageCookBooks controllerManageCookBooks;
    @FXML
    public void initialize() {
        refreshData();
    }

    protected void refreshData() {
        fillTextFields();
    }

    /**
     * set loaded text to textfields
     */
    private void fillTextFields() {
        //Cookbook selection from the ControllerManageCookBooks
        final Cookbook cookbook = controllerManageCookBooks.getListViewCookBooks()
                                                               .getSelectionModel()
                                                               .getSelectedItem();
        textFieldName.setText(cookbook.getTitle());
    }

    private void manageSaveError(String boldPrint, String littlePrint) {
        ControllerDefault controllerDefault = new ControllerDefault();
        controllerDefault.newWindowNotResizable(Resources.getErrorFXML(), Resources.getErrorWindowText());
        controllerError.setLabels(boldPrint, littlePrint);
    }

    @FXML
    void changeCookBook(ActionEvent event) {
        Cookbook cookbook = controllerManageCookBooks.getListViewCookBooks().getSelectionModel().getSelectedItem();
        if(null != cookbook) {
            if(!this.textFieldName.getText().trim().isEmpty()) {
                cookbook.setTitle(textFieldName.getText());
                ui.changeCookBook(cookbook);
                controllerManageCookBooks.refreshListViews();
                controllerManageCookBook.refresh();
            } else {
                manageSaveError("Sie haben die Plfichtfelder nicht ausgefüllt.", "Bitte füllen Sie die Pflichtfelder aus.");
            }
        } else {
            LOG.error("Failed to apply changes of input mask to given object because it can't retried from the list for modification.");
        }
        //Close Stage
        Stage stage = (Stage) changeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * The method ''closeChangeCookBook()'' closes the ChangeCookBook-Window after a interaction with the close-button.
     * @param event
     */
    @FXML
    void closeChangeCookBook(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}


