package sample.ui;

/**
 * @author Tobias Stelter
 */


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ControllerExportCookBook {

    @FXML
    private Button browseButton;

    @FXML
    private Button saveButton;

    @FXML
    private ComboBox<String> comboBoxFormat;

    @FXML
    private MenuItem menuItemA5;

    @FXML
    private MenuItem menuItemA4;

    private ObservableList<String> format;



    @FXML
    private Button closeButton;

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
                                    // DO
                                } else if (item.contains("A5")) {
                                    // DO
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

    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

    }

    @FXML
    void saveCookBook(ActionEvent event){
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();

    }
}
