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

import java.io.File;

public class ControllerExportCookBook {

    File file;
    @FXML
    private Button browseButton;
    @FXML
    private TextField textFieldPath;
    @FXML
    private Button saveButton;
    @FXML
    private ComboBox<String> comboBoxFormat;

    private ObservableList<String> format;

    @FXML
    private Button closeButton;

    private String formatChoice;

    @FXML
    private void initialize() {
        initializeListeners();
        format = FXCollections.observableArrayList("A4", "A5");
        refreshComboBox(format);

    }

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

    @FXML
    void browse(ActionEvent event) {
        FileHandler fileHandler = new FileHandler();
        this.file = fileHandler.exportFile();
        if(this.file != null) {
            textFieldPath.setText(file.getAbsolutePath());
        }
    }

    @FXML
    void saveCookBook(ActionEvent event){
        if ((this.comboBoxFormat.getValue() == "A4") && (this.file != null)){
            ///
            closeWindow();
        }
        if ((this.comboBoxFormat.getValue() == "A5") && (this.file != null)){
            //
            closeWindow();
        }else{}

    }
}
