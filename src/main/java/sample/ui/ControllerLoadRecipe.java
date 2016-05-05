package sample.ui;

/**
 * Created by Tobias on 30.04.2016.
 */
import javafx.fxml.FXML;
import javafx.event.*;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;


public class ControllerLoadRecipe {

    final ToggleGroup group = new ToggleGroup();

    @FXML
    private RadioButton radioButtonHyperLink;

    @FXML
    private RadioButton radioButtonFolder;
    @FXML
    private RadioButton radioButtonFile;


    @FXML
    private TextField hyperLinkTextField;

    @FXML
    private Button loadButton;


    @FXML
    private Button closeButton;


    private boolean editability = false;

    private boolean radioButtonFileBoolean = false;
    private boolean radioButtonFolderBoolean = false;
    private boolean radioButtonLinkBoolean = false;


    @FXML
    public void initialize(){

        groupRadioButtons();
    }

    void groupRadioButtons() {
        radioButtonFile.setToggleGroup(group);
        radioButtonFolder.setToggleGroup(group);
        radioButtonHyperLink.setToggleGroup(group);


    }

    void controllRadioButtons()
    {
        boolean radioButtonFileBoolean = false;
        boolean radioButtonFolderBoolean = false;
        boolean radioButtonLinkBoolean = false;
    }

    void setHyperLinkEditable() {
        hyperLinkTextField.setEditable(true);
        hyperLinkTextField.getStyleClass().clear();
        hyperLinkTextField.getStyleClass().addAll("text-field", "text-input");


    }
    void setHyperLinkNotEditable() {
        hyperLinkTextField.setEditable(false);
        hyperLinkTextField.clear();
        hyperLinkTextField.getStyleClass().clear();
        hyperLinkTextField.getStyleClass().add("textfieldDisabled");

    }

    @FXML
    void changeRadioButtonFolder(ActionEvent event) {
        controllRadioButtons();
        setHyperLinkNotEditable();
        radioButtonFolderBoolean = true;


    }

    @FXML
    void changeRadioButtonFile(ActionEvent event) {
        controllRadioButtons();
        setHyperLinkNotEditable();
        radioButtonFileBoolean = true;




    }


    @FXML
    void changeHyperLinkEditability(ActionEvent event) {
        controllRadioButtons();
         setHyperLinkEditable();
        radioButtonLinkBoolean = true;


    }

    @FXML
    void openFileChooser() {
        FileHandler fileHandler = new FileHandler();
        fileHandler.importFiles();

    }

    @FXML
    void openFolder() {
        FileHandler fileHandler = new FileHandler();
        fileHandler.importFolder();

    }

    void closeStage(){
        Stage stage = (Stage) loadButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void selectOptionsForLoading(ActionEvent event) {
        if (radioButtonLinkBoolean == true) {
            //
        } else if (radioButtonFolderBoolean == true) {
            openFolder();
        } else if (radioButtonFileBoolean == true) {
            openFileChooser();
        }
        closeStage();


    }

    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();


    }


}


