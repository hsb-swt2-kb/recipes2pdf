package sample.ui;

/**
 * Created by Tobias on 30.04.2016.
 */
import javafx.fxml.FXML;
import javafx.event.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class ControllerLoadRecipe {

    @FXML
    private TextField hyperLinkTextField;

    @FXML
    private Button loadButton;


    private boolean editability = false;
    private boolean radioButtonFile = false;
    private boolean radioButtonFolder = false;
    private boolean radioButtonLink = false;

    void setHyperLinkEditable() {
        hyperLinkTextField.setEditable(true);

    }
    void setHyperLinkNotEditable() {
        hyperLinkTextField.setEditable(false);
        hyperLinkTextField.clear();

    }

    @FXML
    void changeHyperLinkEditability(ActionEvent event) {
        if (editability == false)
        {
            editability = true;
            radioButtonLink = true;
            setHyperLinkEditable();
        }else{
            editability = false;
            radioButtonLink = false;
            setHyperLinkNotEditable();
        }

    }

    @FXML
    void openFileChooserSingle() {
        FileHandler fileHandler = new FileHandler();
        fileHandler.importFile();

    }

    @FXML
    void openFileChooserMultiple() {
        FileHandler fileHandler = new FileHandler();
        fileHandler.importFolder();

    }

    void closeStage(){
        Stage stage = (Stage) loadButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void selectOptionsForLoading(ActionEvent event) {
        if (radioButtonLink == true) {
            //
        } else if (radioButtonFile == true) {
            openFileChooserMultiple();
        } else if (radioButtonFolder == true) {
            openFileChooserSingle();
        }
        closeStage();


    }


}


