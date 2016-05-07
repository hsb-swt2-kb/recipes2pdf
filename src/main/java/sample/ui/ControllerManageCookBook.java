package sample.ui;

/**
 * Created by Tobias on 24.04.2016.
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
    ObservableList<String> recipes;
    ObservableList<String> cookbook;
    private ControllerDefault controllerDefault = new ControllerDefault();
    @FXML
    private ListView<String> listViewRecipes;
    @FXML
    private GridPane gridPane;
    @FXML
    private Button plusButton;
    @FXML
    private TextField searchFieldRecipes;
    @FXML
    private TextField searchFieldCookBooks;
    @FXML
    private ListView<String> listViewCookBook;

    @FXML
    private void initialize() {
        /* TESTDATA */
        this.recipes = FXCollections.observableArrayList("Chilli", "Pizza", "Eintopf", "Bohnenauflauf", "Rindsschmorbraten");
        this.cookbook = FXCollections.observableArrayList("Rindsschmorbraten");
         /* TESTDATA END */
        refreshListViews(recipes, cookbook);
        searchInListView(recipes, searchFieldRecipes, listViewRecipes);
        searchInListView(cookbook, searchFieldCookBooks, listViewCookBook);
    }

    void refreshListViews(ObservableList<String> recipes, ObservableList<String> cookbook) {
        this.listViewRecipes.setItems(recipes);
        this.listViewCookBook.setItems(cookbook);
    }


    void searchInListView(ObservableList<String> list, TextField searchField, ListView<String> listView) {
        FilteredList<String> filteredData = new FilteredList<>(list, s -> true);

        searchField.textProperty().addListener(obs -> {
            String filter = searchField.getText();
            if (filter == null || filter.length() == 0) {
                filteredData.setPredicate(s -> true);
            } else {
                filteredData.setPredicate(s -> s.contains(filter));
            }
            listView.setItems(filteredData);
        });
    }

    @FXML
    void delteRecipe(ActionEvent event) {
        controllerDefault.newWindowNotResizable(deleteFXML, deleteText);
        controllerDefault.newWindowNotResizable(noElementSelectedFXML, noElementSelectedText);


    }

    @FXML
    void changeRecipe(ActionEvent event) {
        controllerDefault.newWindow(changeRecipeFXML, changeText, 415, 545);

    }

    @FXML
    void export2pdf(ActionEvent event) {
        controllerDefault.newWindow(exportFXML, export, 290, 200);

    }

    @FXML
    void addRecipe(ActionEvent event) {
        controllerDefault.newWindow(loadRecipeFXML, loadRecipeText, 290, 160);
    }

}
