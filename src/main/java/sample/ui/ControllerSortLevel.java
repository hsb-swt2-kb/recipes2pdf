package sample.ui;

/**
 * @author Tobias Stelter
 * The Class ''ControllerSortLevel'' manages the SortLevel-FXML.
 * It provides methods for adding a sortlevel to a cookbook.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import static sample.ui.ControllerAddCookBook.getInstance;

public class ControllerSortLevel {

    @FXML
    private Button closeButton;
    @FXML
    private Button saveButton;
    @FXML
    private ListView<String> listViewSortLevels;
    @FXML
    private Button plusButton;
    @FXML
    private Button minusButton;
    @FXML
    private ComboBox<String> comboBoxSortLevels;

    /**
     * The ControllerSortLevel initializes the listeners, the sortLevel-list and the comboBox.
     */
    @FXML
    private void initialize() {
        initializeListeners();
        ObservableList<String> sortLevels = FXCollections.observableArrayList("Region", "Saison", "Tageszeit", "Gerichtart", "Kategorie", "Ernährungsart", "Rezeptquelle");
        refreshComboBox(sortLevels);
    }

    /**
     * The method ''initializeListeners()'' initialize the listeners.
     */
    private void initializeListeners() {
        //Adds a sortLevel to the SortLevel-list after a interaction with plus-button.
        plusButton.setOnAction((ActionEvent event) -> {
            String name = comboBoxSortLevels.getSelectionModel().getSelectedItem();
            boolean insite = listViewSortLevels.getItems().contains(name);
            if (name != null && !insite) {
                listViewSortLevels.getItems().addAll(name);
            }
        });
        //Deletes a sortLevel from the SortLevel-list after a interaction with minus-button.
        minusButton.setOnAction((ActionEvent event) -> {
            String name = comboBoxSortLevels.getSelectionModel().getSelectedItem();
            if (name != null) {
                listViewSortLevels.getItems().remove(name);
            }
        });
    }

    /**
     * The method ''refreshComboBox(ObservableList<String> sortLevels)'' refreshes the comboBox.
     */
    private void refreshComboBox(ObservableList<String> sortLevels) {
        comboBoxSortLevels.setItems(sortLevels);
    }

    /**
     * The method ''closeSortLevel()'' closes the SortLevel-Window after a interaction with the close-button.
     * @param event event this method was effected by
     */
    @FXML
    void closeSortLevel(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * The method ''saveSortLevel'' saves the sortLevel and writes back the data to the ControllerAddCookBook.
     * @param event event this method was effected by
     */
    @FXML
    void saveSortLevel(ActionEvent event) {
        if (!listViewSortLevels.getItems().isEmpty())
        {
            getInstance().setSortLevel(listViewSortLevels.getItems());
            //close window
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        }
    }
}
