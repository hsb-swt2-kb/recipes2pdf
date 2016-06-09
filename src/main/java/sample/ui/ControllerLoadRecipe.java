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
import javafx.stage.Stage;
import org.apache.commons.validator.UrlValidator;
import org.controlsfx.control.PopOver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.exceptions.CouldNotParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static sample.ui.UI.addRecipes;

public class ControllerLoadRecipe {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

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

    @FXML
    public void initialize(){
        hyperLinkTextField.disableProperty().bind( radioButtonHyperLink.selectedProperty().not() );
    }

    @FXML
    void openFileChooser() {
        FileHandler fileHandler = new FileHandler();
        final List<File> files = fileHandler.importFiles();
        if (null != files) {
            try {
                addRecipes(files);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * The method ''openFolder()'' opens a filechooser for selecting a folder.
     */
    @FXML
    void openFolder() {
        FileHandler fileHandler = new FileHandler();
        final File file = fileHandler.importFolder();
        if( null != file ) {
            try {
                UI.addRecipesFromFolder(file);
            } catch (FileNotFoundException | CouldNotParseException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Thes method ''closeStage'' closes the loadRecipe-window or the loadRecipe-popOver.
     */
    private void closeStage(){
        try {
            Stage stage = (Stage) loadButton.getScene().getWindow();
            stage.close();
            }
        //If the stage can't close, because its a PopOver, close the PopOver
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
        if ( radioButtonHyperLink.isSelected() && (!this.hyperLinkTextField.getText().trim().isEmpty())) {
            openHyperlink();
        } else if ( radioButtonFolder.isSelected() ) {
            openFolder();
        } else if ( radioButtonFile.isSelected() ) {
            openFileChooser();
        }
        ControllerManageCookBook.getInstance().refresh();
        closeStage();
    }

    private void manageHyperlinkError(String boldPrint, String littlePrint) {
        ControllerDefault controllerDefault = new ControllerDefault();
        controllerDefault.newWindowNotResizable(Resources.getErrorFXML(), Resources.getErrorWindowText());
        ControllerError.getInstance().setLabels(boldPrint, littlePrint);
    }

    private boolean supportedSourceHyperlink() {
        String chefkoch = "chefkoch";
        String weightwatchers = "weightwatchers";
        String url = this.hyperLinkTextField.getText();
        boolean hyperlink = false;
        UrlValidator urlValidator = new UrlValidator(); // TODO: UrlValidator is deprecated.
        //valid URL
        if (!urlValidator.isValid(url)) {
            manageHyperlinkError("Download fehlgeschlagen!", "Geben Sie bitte einen Hyperlink ein!");
        } else if ((!(url.toLowerCase().contains(chefkoch.toLowerCase()))) && (!(url.toLowerCase().contains(weightwatchers.toLowerCase())))) {
            manageHyperlinkError("Falsche Quelle!", "Geben Sie bitte einen WW- o. CK-Link ein!");
        } else if (urlValidator.isValid(url)) {
            hyperlink = true;
        }
        return hyperlink;
    }

    private void openHyperlink() {
        if ( supportedSourceHyperlink() ) {
            try {
                UI.addRecipeFromHyperlink(this.hyperLinkTextField.getText());
            }
            catch(IOException e) {
                LOG.error("Could not load from url", e);
            } catch (CouldNotParseException e) {
                LOG.error("Could not parse the recipe from url", e);
            }
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
