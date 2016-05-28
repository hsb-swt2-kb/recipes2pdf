package sample.ui;

/**
 * @author Tobias Stelter
 * The Class ''ControllerCLoadRecipe'' manages the LoadRecipe-FXML and the LoadRecipePopOver-FXML.
 * It provides methods for adding new recipes.
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;
import sample.exceptions.CouldNotParseException;

import java.io.FileNotFoundException;

import static sample.ui.UI.addRecipes;

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
        setHyperLinkNotEditable();
    }

    /**
     * These method groups the RadioButtons File,Folder and Hyperlink.
     */
    void groupRadioButtons() {
        radioButtonFile.setToggleGroup(group);
        radioButtonFolder.setToggleGroup(group);
        radioButtonHyperLink.setToggleGroup(group);
    }

    /**
     * These method defines the initial state of the opened window.
     */
    void controllRadioButtons()
    {
        boolean radioButtonFileBoolean = false;
        boolean radioButtonFolderBoolean = false;
        boolean radioButtonLinkBoolean = false;
    }

    /**
     * The method ''setHyperLinkEditable()'' sets the  hyperLinkTextField editable.
     */

    void setHyperLinkEditable() {
        hyperLinkTextField.setEditable(true);
        hyperLinkTextField.setDisable(false);
    }

    /**
     * The method ''setHyperLinkNotEditable()'' deactivate the hyperLinkTextField.
     */

    void setHyperLinkNotEditable() {
        hyperLinkTextField.setEditable(false);
        hyperLinkTextField.clear();
        hyperLinkTextField.setDisable(true);
    }

    /**
     * The method ''changeRadioButtonFolder(ActionEvent event)'' sets the options for the folder-selection.
     */

    @FXML
    void changeRadioButtonFolder(ActionEvent event) {
        controllRadioButtons();
        setHyperLinkNotEditable();
        radioButtonFolderBoolean = true;
    }

    /**
     * The method ''changeRadioButtonFile(ActionEvent event)'' sets the options for the file-selection.
     */

    @FXML
    void changeRadioButtonFile(ActionEvent event) {
        controllRadioButtons();
        setHyperLinkNotEditable();
        radioButtonFileBoolean = true;
    }


    /**
     * The method ''changeHyperLinkEditability(ActionEvent event)'' sets the options for the hyperlink-selection.
     */
    @FXML
    void changeHyperLinkEditability(ActionEvent event) {
        controllRadioButtons();
         setHyperLinkEditable();
        radioButtonLinkBoolean = true;
    }

    @FXML
    void openFileChooser() {
        FileHandler fileHandler = new FileHandler();
        try {
            addRecipes(fileHandler.importFiles());
        }
        catch (CouldNotParseException e){
            // TODO: handle exception
        }
        catch(FileNotFoundException e){
            // TODO: handle exception
        }

    }

    /**
     * The method ''openFolder()'' opens a filechooser for selecting a folder.
     */

    @FXML
    void openFolder() {
        FileHandler fileHandler = new FileHandler();
        fileHandler.importFolder();
    }

    /**
     * Thes method ''closeStage'' closes the loadRecipe-window or the loadRecipe-popOver.
     */
    void closeStage(){
        try {
            Stage stage = (Stage) loadButton.getScene().getWindow();
            stage.close();
            }
        //If the stage can't close, beaucause its a PopOver, close the PopOver
        catch (Exception e)
        {
            PopOver popOver = (PopOver) loadButton.getScene().getWindow();
            popOver.hide();
        }
    }

    /**
     * These method selects the options for loading in reference of the RadioButtons and the Load-Button.
     */
    @FXML
    void selectOptionsForLoading(ActionEvent event) {
        if ((radioButtonLinkBoolean == true) && (this.hyperLinkTextField.getText().trim().isEmpty() == false)) {
            System.out.println("(this.hyperLinkTextField.getText()");
            closeStage();
        } else if (radioButtonFolderBoolean == true) {
            openFolder();
            closeStage();
        } else if (radioButtonFileBoolean == true) {
            openFileChooser();
            closeStage();
        }
    }

    /**
     * The method closes the Load-Recipe-Window by interaction with the Close-Button.
     *
     */
    @FXML
    void closeWindow(ActionEvent event) {

            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
    }
}


