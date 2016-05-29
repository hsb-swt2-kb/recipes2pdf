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
    String foreword;
    String path;
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

    //Cookbook selection from the ControllerManageCookBooks
    private String name = ControllerManageCookBooks.getInstance().getSelectedItem();

    @FXML
    public void initialize() {
        refreshData();


    }

    protected void refreshData() {
        loadInformation();
        fillTextFields();
    }

    /**
     * load cookbook information
     */

    private void loadInformation() {
        Cookbook cookbook = UI.searchCookBook(name);
        foreword = cookbook.getForeword();
        path = cookbook.getPathOfpicture();
    }

    /**
     * set loaded text to textfields
     */

    private void fillTextFields() {

        textFieldName.setText(name);
        textAreaVorwort.setText(foreword);
        textFieldPicture.setText(path);
    }

    @FXML
    void changeCookBook(ActionEvent event) {
        getName();
        getForeWord();
        if(this.textFieldName.getText().trim().isEmpty() == false) {
            System.out.println(name + foreword + "file.getName()");
            //Close Stage
            Stage stage = (Stage) changeButton.getScene().getWindow();
            stage.close();
        }else{
            //Exception
        }

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
            path = file.getAbsolutePath();
            textFieldPicture.setText(path);
        }

    }

    private String getForeWord(){
        foreword = textAreaVorwort.getText();
        return foreword;
    }
    private String getName(){
        name = textFieldName.getText();
        return  name;
    }

    }


