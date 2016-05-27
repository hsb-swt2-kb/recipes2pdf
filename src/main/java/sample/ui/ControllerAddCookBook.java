package sample.ui;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;

/**
 * Created by Tobias on 26.05.2016.
 */
public class ControllerAddCookBook {

    private static ControllerAddCookBook ourInstance = new ControllerAddCookBook();

    public static ControllerAddCookBook getInstance() {
        return ourInstance;
    }

    @FXML
    private Button closeButton;

    @FXML
    private Button buttonSortLevel;

    @FXML
    private ListView<String> listViewSortLevel;

    @FXML
    private TextField textFieldPicture;

    @FXML
    private TextField textFieldName;

    @FXML
    private TextArea textAreaVorwort;

    private ObservableList<String> sortLevelsOfTheCookbook;

    @FXML
    private Button generateButton;

    File file;
    String name;
    String foreword;


    @FXML
    private void initialize() {
        this.sortLevelsOfTheCookbook= FXCollections.observableArrayList();
    }

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
        this.file = fileHandler.importPicture();
        if(file != null) {
            textFieldPicture.setText(file.getAbsolutePath());
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



    @FXML
    void openSortLevel(ActionEvent event) {
        ControllerDefault controllerDefault = new ControllerDefault();
        controllerDefault.newWindow(Resources.getSortLevelFXML(), Resources.getSortLevelWindowText(), 300, 200, Resources.getDefaultIcon());

    }


    @FXML
    void generateCookBook(ActionEvent event) {
        getName();
        getForeWord();
        if(this.textFieldName.getText().trim().isEmpty() == false) {
            System.out.println(name + foreword + "file.getName()");
            //Close Stage
            Stage stage = (Stage) generateButton.getScene().getWindow();
            stage.close();
        }else{
            //Exception
        }

    }

}
