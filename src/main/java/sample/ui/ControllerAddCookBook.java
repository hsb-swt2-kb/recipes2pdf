package sample.ui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;

import static org.apache.commons.io.FileUtils.getFile;

/**
 * @author Tobias Stelter
 * The Class ''ControllerAddCookBook'' manages the AddCookBook-FXML.
 * It provides methods for adding a new cookbook.
 * The cookbook contains at least a name and a sortlevel - a foreword and a picture are optional.
 */

public class ControllerAddCookBook  {

    private static ControllerAddCookBook instance;
    File file;
    String name;
    String foreword;
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
    private Button browseButton;
    @FXML
    private TextArea textAreaVorwort;
    private ObservableList<String> sortLevelsOfTheCookbook;
    @FXML
    private Button generateButton;

    /**
     * The method ''getInstance'' returns the controllerInstance for passing data beetween the ControllerAddCookBook and ControllerSortLevel.
     *
     * @return controllerInstance
     */

    public static ControllerAddCookBook getInstance() {

        if (ControllerAddCookBook.instance == null) {
            synchronized (ControllerAddCookBook.class) {
                if (ControllerAddCookBook.instance == null) {
                    ControllerAddCookBook.instance = new ControllerAddCookBook();
                }
            }
        }
        return ControllerAddCookBook.instance;
    }

    /**
     * The ControllerAddCookBook initializes the ControllerInstance and the SortLevel-list.
     */

    @FXML
    private void initialize() {

        instance = this;
        this.sortLevelsOfTheCookbook= FXCollections.observableArrayList();
    }

    /**
     * The method ''closeAddCookBook()'' closes the AddCookBook-Window after a interaction with the close-button.
     * @param event
     */

    @FXML
    void closeAddCookBook(ActionEvent event) {
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


    /**
     * The method ''openSortLevel()'' opens the SortLevel-Window after a interaction with the sortLevel-button.
     * @param event
     */
    @FXML
    void openSortLevel(ActionEvent event) {
        ControllerDefault controllerDefault = new ControllerDefault();
        controllerDefault.newWindow(Resources.getSortLevelFXML(), Resources.getSortLevelWindowText(), 300, 200, Resources.getDefaultIcon());

    }

    /**
     * The method ''setSortLevel()'' sets the SortLevel and refreshs the SortLevel-ListView.
     * It will used in ControllerSortLevel after a interaction with the save-Button.
     * @param sortLevelsOfTheCookbook
     */
    void setSortLevel(ObservableList<String> sortLevelsOfTheCookbook){


        this.sortLevelsOfTheCookbook = sortLevelsOfTheCookbook;
        this.listViewSortLevel.getItems().clear();
        this.listViewSortLevel.getItems().addAll(this.sortLevelsOfTheCookbook);
    }

    private void manageSaveError(String boldPrint, String littlePrint) {
        ControllerDefault controllerDefault = new ControllerDefault();
        controllerDefault.newWindowNotResizable(Resources.getErrorFXML(), Resources.getErrorWindowText());
        ControllerError.getInstance().setLabels(boldPrint, littlePrint);
    }

    /**
     * The method ''generateCookBook()'' finally generates the cookbook,
     * when at least the name and the sortlevel setted.
     * @param event
     */
    @FXML
    void generateCookBook(ActionEvent event) {
        if((this.textFieldName.getText().trim().isEmpty() == false) && (this.listViewSortLevel.getItems().isEmpty() == false)) {
            UI.addCookBook(getName(), sortLevelsOfTheCookbook, getForeWord(), getFile());
            ControllerManageCookBook.getInstance().refresh();
            ControllerManageCookBooks.getInstance().refreshListViews();
            //Close Stage
            Stage stage = (Stage) generateButton.getScene().getWindow();
            stage.close();
        }else{
            manageSaveError("Sie haben die Plfichtfelder nicht ausgefüllt.", "Bitte füllen Sie die Pflichtfelder aus.");
        }


    }

}
