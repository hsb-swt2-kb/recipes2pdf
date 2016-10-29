package sample.ui;

/**
 * @author Tobias Stelter
 * The Class ''ControllerChangeCookBook'' manages the ChangeCookBook-FXML.
 * It displays the cookbook-data and provides methods for changing a cookbook.
 * The cookbook contains at least a name and a sortlevel - a foreword and a picture are optional.
 */


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.model.Cookbook;

import java.io.File;

public class ControllerChangeCookBook {

    File file;
    Cookbook cookbook;

    @FXML
    private Button fileChooserButton;
    @FXML
    private Button closeButton;
    @FXML
    private TextField textFieldName;
    @FXML
    private TextArea textAreaVorwort;
    @FXML
    private TextField textFieldPicture;
    @FXML
    private Button changeButton;
    UI ui;

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
        textFieldName.setText(ControllerManageCookBooks.getInstance().getSelectedItem());
        textAreaVorwort.setText("");
        textFieldPicture.setText("");
    }

    private void manageSaveError(String boldPrint, String littlePrint) {
        ControllerDefault controllerDefault = new ControllerDefault();
        controllerDefault.newWindowNotResizable(Resources.getErrorFXML(), Resources.getErrorWindowText());
        ControllerError.getInstance().setLabels(boldPrint, littlePrint);
    }

    @FXML
    void changeCookBook(ActionEvent event) {
        if(this.textFieldName.getText().trim().isEmpty() == false) {
            try {
                cookbook.setTitle(textFieldName.getText());
                ui.changeCookBook(cookbook);
                ControllerManageCookBooks.getInstance().refreshListViews();
                ControllerManageCookBook.getInstance().refresh();
            }catch (Exception e)
            {
                System.out.println("Couldn't load cookbook");
            }
        } else {
            manageSaveError("Sie haben die Plfichtfelder nicht ausgefüllt.", "Bitte füllen Sie die Pflichtfelder aus.");
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

    /**
     * The method ''openFileChooser()'' opens the filechooser after a interaction with the browse-Button.
     * If the user imports a picture, the path will display in the textField-picture.
     * @param event
     */
    @FXML
    void openFileChooser(ActionEvent event) {
        FileHandler fileHandler = new FileHandler();
        this.file = fileHandler.importPicture();
        if(this.file != null) {
            textFieldPicture.setText(file.getAbsolutePath());
        }
    }
}


