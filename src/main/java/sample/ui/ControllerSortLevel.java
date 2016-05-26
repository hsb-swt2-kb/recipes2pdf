package sample.ui;

/**
 * Created by Tobias on 18.05.2016.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

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

    private ObservableList<String> sortLevels;
    private ObservableList<String> sortLevelsOfTheCookbook;


    @FXML
    private void initialize() {
        initializeListeners();
        this.sortLevels = FXCollections.observableArrayList("Region", "Saison", "Tageszeit", "Gerichtart", "Kategorie", "Ernährungsart", "Rezeptquelle");
        refreshComboBox(sortLevels);
    }


    private void initializeListeners() {
        plusButton.setOnAction((ActionEvent event) -> {
            String name = comboBoxSortLevels.getSelectionModel().getSelectedItem();
            boolean insite = listViewSortLevels.getItems().contains(name);
            if (name != null && insite==false) {
                listViewSortLevels.getItems().addAll(name);
            }
        });
        minusButton.setOnAction((ActionEvent event) -> {
            String name = comboBoxSortLevels.getSelectionModel().getSelectedItem();
            if (name != null) {
                listViewSortLevels.getItems().remove(name);
            }
        });
    }

        private void refreshComboBox(ObservableList<String> sortLevels) {
        comboBoxSortLevels.setItems(sortLevels);
    }


    @FXML
    void closeSortLevel(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

    }

    @FXML
    void saveSortLevel(ActionEvent event) {
        if (listViewSortLevels.getItems().isEmpty()==false)
        {
            listViewSortLevels.getItems(); // <--- ObserVableList für Kai
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();

        }

    }


}
