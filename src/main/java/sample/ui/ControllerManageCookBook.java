package sample.ui;

/**
 * @author Tobias Stelter
 *
 */


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import org.apache.commons.lang3.StringUtils;


public class ControllerManageCookBook {


    private ObservableList<String> recipes;
    private ObservableList<String> cookbook;
    private ObservableList<String> cookbooks;
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
    private Button changeRecipeButton;

    @FXML
    private ComboBox<String> comboBoxCookBooks;

    @FXML
    private Button delteButtonRecipe;

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
        this.cookbooks = FXCollections.observableArrayList("Tobias Kochbuch", "Henriks Kochbuch", "Florians Kochbuch", "Danys Kochbuch", "Christians Kochbuch", "Markuss Kochbuch", "Kais Kochbuch");
         /* TESTDATA END */
        refreshComboBox(cookbooks);
        refreshListViews(recipes, cookbook);
    }

    private void refreshListViews(ObservableList<String> recipes, ObservableList<String> cookbook) {
        FXCollections.sort(recipes);
        FXCollections.sort(cookbook);
        this.listViewRecipes.setItems(recipes);
        this.listViewCookBook.setItems(cookbook);
        searchInListView(recipes, searchFieldRecipes, listViewRecipes);
        searchInListView(cookbook, searchFieldCookBooks, listViewCookBook);
    }

    private void refreshComboBox(ObservableList<String> cookbooks) {
        comboBoxCookBooks.setItems(cookbooks);
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

        leftArrowButton.setOnAction((ActionEvent event) -> {
            String recipe = listViewCookBook.getSelectionModel().getSelectedItem();
            if (recipe != null) {
                listViewCookBook.getSelectionModel().clearSelection();
                cookbook.remove(recipe);
            } else {
                controllerDefault.newWindowNotResizable(Resources.getNoElementsSelectedFXML(), Resources.getErrorWindowText());
            }
        });

        rightArrowButton.setOnAction((ActionEvent event) -> {
            String recipe = listViewRecipes.getSelectionModel().getSelectedItem();
            if (recipe != null) {
                listViewRecipes.getSelectionModel().clearSelection();
                listViewCookBook.getItems().addAll(recipe);
            } else {
                controllerDefault.newWindowNotResizable(Resources.getNoElementsSelectedFXML(), Resources.getErrorWindowText());
            }
        });
        delteButtonRecipe.setOnAction((ActionEvent event) -> {
            String recipe = listViewRecipes.getSelectionModel().getSelectedItem();
            String recipeInCookBook = listViewCookBook.getSelectionModel().getSelectedItem();
            if (recipe != null || recipeInCookBook != null) {
                controllerDefault.newWindowNotResizable(Resources.getDeleteFXML(), Resources.getDeleteWindowText());
            } else {
                controllerDefault.newWindowNotResizable(Resources.getNoElementsSelectedFXML(), Resources.getErrorWindowText());
            }
        });
        changeRecipeButton.setOnAction((ActionEvent event) -> {
            String recipe = listViewRecipes.getSelectionModel().getSelectedItem();
            String recipeInCookBook = listViewCookBook.getSelectionModel().getSelectedItem();
            if (recipe != null || recipeInCookBook != null) {
                controllerDefault.newWindow(Resources.getChangeRecipeFXML(), Resources.getChangeRecipeWindowText(), 415, 545, Resources.getDefaultIcon());
            } else {
                controllerDefault.newWindowNotResizable(Resources.getNoElementsSelectedFXML(), Resources.getErrorWindowText());
            }
        });

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
            if (filter == null || filter.isEmpty()) {
                filteredData.setPredicate(s -> true);
            } else {
                filteredData.setPredicate(s -> StringUtils.containsIgnoreCase( s, filter ));
            }
            listView.setItems(filteredData);
        });
    }


    @FXML
    void export2pdf(ActionEvent event) {
        controllerDefault.newWindow(Resources.getExportFXML(), Resources.getExportWindowText(), 290, 200, Resources.getDefaultIcon());

    }

    @FXML
    void addRecipe(ActionEvent event) {
        controllerDefault.newWindow(Resources.getloadRecipeFXML(), Resources.getLoadWindowText(), 290, 160, Resources.getDefaultIcon());
    }

}
