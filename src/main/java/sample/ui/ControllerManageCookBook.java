package sample.ui;

/**
 * @author Tobias Stelter
 */


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;


public class ControllerManageCookBook {

    private final String exportFXML = "/sample/ui/ExportCookBook.fxml";
    private final String export = "Exportieren";
    private final String deleteFXML = "/sample/ui/DeleteDialog.fxml";
    private final String noElementSelectedFXML = "/sample/ui/NoElementSelectedDialog.fxml";
    private final String defaultIconPath = "/sample/ui/icon_bg_small.png";
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
    private Button leftArrowButton;

    @FXML
    private Button rightArrowButton;

    @FXML
    private TextField searchFieldCookBooks;
    @FXML
    private ListView<String> listViewCookBook;

    @FXML
    private void initialize() {
        initializeListeners();
        /* TESTDATA */
        this.recipes = FXCollections.observableArrayList("Chilli", "Pizza", "Eintopf", "Bohnenauflauf", "Rindsschmorbraten", "Veganes basisches Chili", "Curry aus Süßkartoffel-Streifen", "Gegrillte Mettbrötchen", "Schwälmer Zwiebelplatz", "Bärlauch - Sahnesuppe mit Croutons", "EIS", "Cheeseburgerauflauf", "Tomahawk Steak", "Tijuana Coffee Chili", "Rindersteak mit Pilzen", "Spaghetti in cremiger Brokkoli-Hackleisch-Sauce", "Flankrolle mit Ananas-Tomaten-Salsa");
        this.cookbook = FXCollections.observableArrayList("Rindsschmorbraten", "Tomahawk Steak", "Veganes basisches Chili", "Cheeseburgerauflauf", "Curry aus Süßkartoffel-Streifen");
         /* TESTDATA END */
        refreshListViews(recipes, cookbook);
        searchInListView(recipes, searchFieldRecipes, listViewRecipes);
        searchInListView(cookbook, searchFieldCookBooks, listViewCookBook);
    }

    void refreshListViews(ObservableList<String> recipes, ObservableList<String> cookbook) {
        FXCollections.sort(recipes);
        FXCollections.sort(cookbook);
        this.listViewRecipes.setItems(recipes);
        this.listViewCookBook.setItems(cookbook);
    }

    private void initializeListeners() {
        // drag from left to right
        listViewRecipes.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (listViewRecipes.getSelectionModel().getSelectedItem() == null) {
                    return;
                }

                Dragboard dragBoard = listViewRecipes.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(listViewRecipes.getSelectionModel().getSelectedItem());
                dragBoard.setContent(content);
            }
        });
        // drag from right to left
        listViewCookBook.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard dragBoard = listViewCookBook.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(listViewCookBook.getSelectionModel().getSelectedItem());
                dragBoard.setContent(content);
            }
        });

        listViewCookBook.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                dragEvent.acceptTransferModes(TransferMode.MOVE);
            }
        });

        listViewCookBook.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                String selectedItem = dragEvent.getDragboard().getString();
                listViewCookBook.getItems().addAll(selectedItem);
                listViewRecipes.setItems(recipes);
                dragEvent.setDropCompleted(true);
            }
        });

        listViewRecipes.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                dragEvent.acceptTransferModes(TransferMode.MOVE);
            }
        });

        listViewRecipes.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                String selectedItem = dragEvent.getDragboard().getString();


                cookbook.remove(selectedItem);
                dragEvent.setDropCompleted(true);
            }
        });
    }

    @FXML
    void moveRecipeToCookBook(ActionEvent event) {

    }

    @FXML
    void delteRecipeFromCookBook(ActionEvent event) {

    }


    /**
     * These method enables searching in a ListView. The specify list will bei filtered and sorted.
     *
     * @param list        defines the observable list for searching
     * @param searchField searchfield defines the searchField for the search
     * @param listView    listView defines the listView for the search
     */
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
        controllerDefault.newWindow(changeRecipeFXML, changeText, 415, 545, defaultIconPath);

    }

    @FXML
    void export2pdf(ActionEvent event) {
        controllerDefault.newWindow(exportFXML, export, 290, 200, defaultIconPath);

    }

    @FXML
    void addRecipe(ActionEvent event) {
        controllerDefault.newWindow(loadRecipeFXML, loadRecipeText, 290, 160, defaultIconPath);
    }

}
