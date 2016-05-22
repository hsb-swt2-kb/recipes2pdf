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
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import org.apache.commons.lang3.StringUtils;
import org.controlsfx.control.PopOver;

import java.io.IOException;


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
        setupMultipleSelection();

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
            String name =listViewCookBook.getSelectionModel().getSelectedItem();
                if (name != null) {
                    cookbook.remove(name);
                } else {
                    controllerDefault.newWindowNotResizable(Resources.getNoElementsSelectedFXML(), Resources.getErrorWindowText());
                }
            listViewCookBook.getSelectionModel().clearSelection();
        });

        rightArrowButton.setOnAction((ActionEvent event) -> {
            String name =listViewRecipes.getSelectionModel().getSelectedItem();
                if (name != null) {
                    listViewCookBook.getItems().addAll(name);
                } else {
                    controllerDefault.newWindowNotResizable(Resources.getNoElementsSelectedFXML(), Resources.getErrorWindowText());
                }
            listViewRecipes.getSelectionModel().clearSelection();
        });



        delteButtonRecipe.setOnAction((ActionEvent event) -> {
            String recipe =listViewRecipes.getSelectionModel().getSelectedItem();
                String recipeInCookBook = listViewCookBook.getSelectionModel().getSelectedItem();
                System.out.println("Would delete " + recipe); //TODO: Consider choice of user to really delete
                if (recipe != null || recipeInCookBook != null) {
                    controllerDefault.newWindowNotResizable(Resources.getDeleteFXML(), Resources.getDeleteWindowText());
                } else {
                    controllerDefault.newWindowNotResizable(Resources.getNoElementsSelectedFXML(), Resources.getErrorWindowText());
                }
            });


        changeRecipeButton.setOnAction((ActionEvent event) -> {
            String recipe =listViewRecipes.getSelectionModel().getSelectedItem();
                String recipeInCookBook = listViewCookBook.getSelectionModel().getSelectedItem();
                System.out.println("Would change " + recipe); //TODO: Consider choice of user to change
                if (recipe != null || recipeInCookBook != null) {
                    controllerDefault.newWindowNotResizable(Resources.getChangeRecipeFXML(), Resources.getChangeRecipeWindowText());
                } else {
                    controllerDefault.newWindowNotResizable(Resources.getNoElementsSelectedFXML(), Resources.getErrorWindowText());
                }
            });

    }

    /**
     * Setup multiple selection for listviews.
     */
    private void setupMultipleSelection() {
        listViewRecipes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
                filteredData.setPredicate(s -> StringUtils.containsIgnoreCase(s, filter));
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
        Node node = loadResource(Resources.getloadRecipePopOverFXML());
        PopOver popOver = new PopOver(node);
        popOver.setDetachable(false);
        popOver.setCornerRadius(4);
        popOver.setArrowLocation(PopOver.ArrowLocation.BOTTOM_LEFT);
        popOver.show(this.plusButton);
        //controllerDefault.newWindow(Resources.getloadRecipeFXML(), Resources.getLoadWindowText(), 290, 160, Resources.getDefaultIcon());
    }

    public Node loadResource(String fxml) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Path is wrong");
        }
        return root;
    }

}
