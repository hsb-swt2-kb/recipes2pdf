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
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.builder.Exceptions.TexParserException;
import sample.exceptions.CookBookNotFoundException;

import java.awt.*;
import java.io.File;
import java.io.IOException;


public class ControllerExportCookBook {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    private String formatChoice;
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
        initializeListeners();
        ObservableList<String> format = FXCollections.observableArrayList("A4", "A5");
        comboBoxFormat.setItems(format);
        comboBoxFormat.getSelectionModel().selectFirst();
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
                                if (item.contains("A4")) formatChoice = "A4";
                                else if (item.contains("A5")) formatChoice = "A5";
                            } else setText(null);
                        }
                    };
                    return cell;
                }
            });
    }

    /**
     * The method ''closeCWindow(ActionEvent event)'' closes the export-window after a interaction with the close-button.
     *
     * @param event event this method was effected by
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

    private void manageSaveError(String boldPrint, String littlePrint) {
        ControllerDefault controllerDefault = new ControllerDefault();
        controllerDefault.newWindowNotResizable(Resources.getErrorFXML(), Resources.getErrorWindowText());
        ControllerError.getInstance().setLabels(boldPrint, littlePrint);
    }

    @FXML
    void saveCookBook(ActionEvent event) {
        try {
            final File pdfFile = UI.exportCookbook(ControllerManageCookBook.getInstance().getSelectedCookBooks(), formatChoice);
            if (null != pdfFile && Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(pdfFile);
            }
            this.closeWindow();
        } catch (CookBookNotFoundException | IOException | TexParserException e) {
            manageSaveError("Upps", "Da ist wohl was schief gegenagen.");
            LOG.error("Failed to generate cookbook", e);
        }
    }
}
