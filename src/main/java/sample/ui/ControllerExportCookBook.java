package sample.ui;

/**
 * @author Tobias Stelter
 * The Class ''ControllerExport'' manages the ExportCookBook-FXML.
 * It provides methods for a pdf-export of a cookbook in the choosen format.
 */


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.builder.Exceptions.TexParserException;
import sample.exceptions.CookBookNotFoundException;

import java.io.File;
import java.io.IOException;


public class ControllerExportCookBook {
    final Logger LOG = LoggerFactory.getLogger(this.getClass());
    File file;

    private String selectedCookBook;
    private String formatChoice;
    private ObservableList<String> format;

    @FXML
    private Button browseButton;
    @FXML
    private TextField textFieldPath;
    @FXML
    private Button saveButton;
    @FXML
    private ComboBox<String> comboBoxFormat;
    @FXML
    private Button closeButton;

    @FXML
    private void initialize() {
        this.selectedCookBook = ControllerManageCookBook.getInstance().getSelectedCookBooks();
        initializeListeners();
        format = FXCollections.observableArrayList("A4", "A5");
        refreshComboBox(format);
    }

    /**
     * The method ''initializeListeners()'' initializes the listeners.
     */
    private void initializeListeners() {
        comboBoxFormat
            .setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
                @Override
                public ListCell<String> call(ListView<String> param) {
                    final ListCell<String> cell = new ListCell<String>() {
                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item != null) {
                                setText(item);
                                if (item.contains("A4")) {
                                } else if (item.contains("A5")) {
                                }
                            } else {
                                setText(null);
                            }
                        }
                    };
                    return cell;
                }

            });
    }

    private void refreshComboBox(ObservableList<String> format) {
        comboBoxFormat.setItems(format);
    }

    /**
     * The method ''closeCWindow(ActionEvent event)'' closes the export-window after a interaction with the close-button.
     *
     * @param event
     */
    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * The method ''closeCWindow()'' closes the export-window.
     */
    @FXML
    void closeWindow() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    /**
     * The method '' browse(ActionEvent event)'' opens the filechooser and set the choosen path to the path-textField.
     */
    @FXML
    void browse(ActionEvent event) {
        FileHandler fileHandler = new FileHandler();
        this.file = fileHandler.exportFile();
        if(this.file != null) {
            textFieldPath.setText(file.getAbsolutePath());
        }
    }

    private void manageSaveError(String boldPrint, String littlePrint) {
        ControllerDefault controllerDefault = new ControllerDefault();
        controllerDefault.newWindowNotResizable(Resources.getErrorFXML(), Resources.getErrorWindowText());
        ControllerError.getInstance().setLabels(boldPrint, littlePrint);
    }

    @FXML
    void saveCookBook(ActionEvent event) {
        try {
            UI.exportCookbook(ControllerManageCookBook.getInstance().getSelectedCookBooks(), "A4");
        } catch (CookBookNotFoundException e) {
            manageSaveError("Upps", "Da ist wohl was schief gegenagen.");
        } catch (IOException e) {
            manageSaveError("Upps", "Da ist wohl was schief gegenagen.");
        } catch (TexParserException e) {
            manageSaveError("Upps", "Da ist wohl was schief gegenagen.");
        }
    }
}
