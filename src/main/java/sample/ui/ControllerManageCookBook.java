package sample.ui;

/**
 * Created by Tobias on 24.04.2016.
 */

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.*;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ControllerManageCookBook {

    private final String exportFXML = "/sample/ui/ExportCookBook.fxml";
    private final String export = "Exportieren";
    private final String deleteFXML = "/sample/ui/DeleteDialog.fxml";
    private final String delete = "Löschen";

    ControllerDefault controllerDefault = new ControllerDefault();

    @FXML
    private ListView<String> listViewRecipes;

    @FXML
    private ListView<String> listViewCookBook;

    @FXML
    private Button delteButtonCookBook;

    @FXML
    private Button delteButtonRecipe;

    @FXML
    private Button export2pdfButton;

    @FXML
    void delteCookBook(ActionEvent event) {
        controllerDefault.newWindow(deleteFXML, delete);


    }

    void refreshListViews(ObservableList<String> recipes, ObservableList<String> cookbook){
        this.listViewRecipes.setItems(recipes);
        this.listViewCookBook.setItems(cookbook);
    }

    @FXML
    void delteRecipe(ActionEvent event) {
        controllerDefault.newWindow(deleteFXML, delete);

        //Test
        ObservableList<String> recipes = FXCollections.observableArrayList ("Chilli", "Pizza", "Eintopf", "Bohnenauflauf");
        ObservableList<String> cookbook = FXCollections.observableArrayList ("Rindsschmorbraten");
        refreshListViews(recipes, cookbook);

    }

    @FXML
    void export2pdf(ActionEvent event) {
        controllerDefault.newWindow(exportFXML, export);

    }

}
