package sample.ui;

/**
 * Created by Tobias on 24.04.2016.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;


public class ControllerManageCookBook {

    private final String exportFXML = "/sample/ui/ExportCookBook.fxml";
    private final String export = "Exportieren";
    private final String deleteFXML = "/sample/ui/DeleteDialog.fxml";
    private final String noElementSelectedFXML = "/sample/ui/NoElementSelectedDialog.fxml";
    private final String changeRecipeFXML = "/sample/ui/ChangeRecipe.fxml";
    private final String loadRecipeFXML = "/sample/ui/LoadRecipe.fxml";
    private final String loadRecipeText = "Rezept laden";
    private final String deleteText = "Löschen";
    private final String noElementSelectedText = "Error";
        private final String changeText = "Rezept ändern";



    private ControllerDefault controllerDefault = new ControllerDefault();

    @FXML
    private ListView<String> listViewRecipes;

    @FXML
    private GridPane gridPane;

    @FXML
    private Button plusButton;


    @FXML
    private ListView<String> listViewCookBook;


    void refreshListViews(ObservableList<String> recipes, ObservableList<String> cookbook){
        this.listViewRecipes.setItems(recipes);
        this.listViewCookBook.setItems(cookbook);
    }

    @FXML
    void delteRecipe(ActionEvent event) {
        controllerDefault.newWindow(deleteFXML, deleteText);
        controllerDefault.newWindow(noElementSelectedFXML, noElementSelectedText);

        //Test
        ObservableList<String> recipes = FXCollections.observableArrayList ("Chilli", "Pizza", "Eintopf", "Bohnenauflauf","Rindsschmorbraten");
        ObservableList<String> cookbook = FXCollections.observableArrayList ("Rindsschmorbraten");
        refreshListViews(recipes, cookbook);

    }

    @FXML
    void changeRecipe(ActionEvent event) {
        controllerDefault.newWindow(changeRecipeFXML, changeText);

    }

    @FXML
    void export2pdf(ActionEvent event) {
        controllerDefault.newWindow(exportFXML, export);

    }

    @FXML
    void addRecipe(ActionEvent event) {
        controllerDefault.newWindow(loadRecipeFXML, loadRecipeText);
    }

}
