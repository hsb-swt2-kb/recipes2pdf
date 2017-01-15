package sample.ui;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import org.apache.commons.lang3.NotImplementedException;
import org.controlsfx.control.PopOver;
import org.fxmisc.easybind.EasyBind;
import sample.exceptions.CookBookNotFoundException;
import sample.model.Cookbook;
import sample.model.Recipe;
import sample.ui.adapter.CookbookAdapter;
import sample.ui.adapter.RecipeAdapter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by noex_ on 30.10.2016.
 */
@Singleton
public class ControllerManageCookBook implements Initializable {

    @FXML
    private GridPane gridPane;
    @FXML
    private JFXButton plusButton;
    @FXML
    private TextField searchFieldRecipes;
    @FXML
    private JFXButton changeRecipeButton;

    @FXML
    private JFXButton toRight;

    @FXML
    private JFXButton toLeft;
    @FXML
    private TextField searchFieldCookBooks;

    @FXML
    private JFXButton deleteButtonRecipe;

    @FXML
    ListView<Recipe> listViewRecipes;

    @FXML
    ListView<Recipe> listViewRecipesInCookbook;

    @FXML
    ComboBox<Cookbook> comboBoxCookBooks;

    @Inject
    UI ui;

    @Inject
    FXMLLoader fxmlLoader;

    @Inject
    ControllerDefault controllerDefault;

    private Cookbook selectedCookbook;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        comboBoxCookBooks.setCellFactory(new CookbookAdapter());
        comboBoxCookBooks.setConverter(new StringConverter<Cookbook>() {
            @Override
            public String toString(Cookbook cookbook) {
                return cookbook.getTitle();
            }

            @Override
            public Cookbook fromString(String string) {
                return null;
            }
        });


        comboBoxCookBooks.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedCookbook = newValue;
            try {
                ObservableList<Recipe> recipesOfCookbook = FXCollections.observableArrayList( ui.getRecipesOfCookbook(selectedCookbook) );
                EasyBind.listBind(listViewRecipesInCookbook.getItems(), recipesOfCookbook);
            } catch (CookBookNotFoundException e) {
                e.printStackTrace();
            }
        });
        listViewRecipes.setCellFactory(new RecipeAdapter());
        listViewRecipesInCookbook.setCellFactory(new RecipeAdapter());
        toLeft.setOnAction(this::toLeft);
        toRight.setOnAction(this::toRight);

        buttonActions();
        populateData();
    }

    private void buttonActions() {
        deleteButtonRecipe.setOnAction((ActionEvent event) -> {
            Recipe recipe = listViewRecipes.getSelectionModel().getSelectedItem();
            Recipe recipeInCookBook = listViewRecipesInCookbook.getSelectionModel().getSelectedItem();
            if (recipe != null || recipeInCookBook != null) {
                controllerDefault.newWindowNotResizable(Resources.getDeleteRecipeFXML(), Resources.getDeleteWindowText());
            } else {
                manageSaveError("Sie haben kein Element ausgwählt.", "Bitte wählen Sie ein Rezept aus.");
            }
        });
        changeRecipeButton.setOnAction((ActionEvent event) -> {
            Recipe recipe = listViewRecipes.getSelectionModel().getSelectedItem();
            Recipe recipeInCookBook = listViewRecipesInCookbook.getSelectionModel().getSelectedItem();
            if (recipe != null || recipeInCookBook != null) {
                controllerDefault.newWindow(Resources.getChangeCookBooksFXML(), Resources.getChangeCookBookWindowText(), 370, 245, Resources.getDefaultIcon());
            } else {
                manageSaveError("Sie haben kein Element ausgwählt.", "Bitte wählen Sie ein Kochbuch aus.");
            }
        });
    }

    private void populateData() {
        comboBoxCookBooks.getItems().setAll(  ui.getAllCookbooksFromDB() );
        comboBoxCookBooks.selectionModelProperty().get().selectFirst();

        try {
            ObservableList<Recipe> recipesOfCookbook = FXCollections.observableArrayList( ui.getRecipesOfCookbook(selectedCookbook) );
            EasyBind.listBind(listViewRecipesInCookbook.getItems(), recipesOfCookbook);
        } catch (CookBookNotFoundException e) {
            e.printStackTrace();
        }
        ui.getAllRecipesFromDB().forEach( recipe -> {
            if( !listViewRecipesInCookbook.getItems().contains(recipe)) {
                // Only show left if is not already in the cookbook
                listViewRecipes.getItems().add(recipe);
            }
        });
    }

    private void toRight(ActionEvent actionEvent) {
        if ( null != selectedCookbook ) {
            listViewRecipes.getSelectionModel().getSelectedItems().forEach(recipe -> {
                listViewRecipesInCookbook.getItems().add(recipe);
                listViewRecipes.getItems().removeAll(recipe);
                ui.addRecipeToCookbook(selectedCookbook, recipe);
            });
            System.out.println("toRight");
        } else {
            manageSaveError("Sie haben kein Element ausgwählt.", "Bitte wählen Sie ein Kochbuch aus.");
        }
    }

    private void toLeft(ActionEvent event) {
        if ( null != selectedCookbook ) {
            listViewRecipesInCookbook.getSelectionModel().getSelectedItems().forEach(recipe -> {
                listViewRecipes.getItems().add(recipe);
                listViewRecipesInCookbook.getItems().removeAll(recipe);
                ui.removeRecipeFromCookbook(selectedCookbook, recipe);
            });
            System.out.println("toLeft");
        } else {
            manageSaveError("Sie haben kein Element ausgwählt.", "Bitte wählen Sie ein Kochbuch aus.");
        }
    }

    private void manageSaveError(String error, String description) {
        //TODO: window
        throw new IllegalStateException(error + ", " + description);
    }

    public void refresh() {
        populateData();
    }


    /**
     * The method ''export2pdf()'' opens the export-window after a interaction with the export-button.
     *
     * @param event
     */
    @FXML
    void export2pdf(ActionEvent event) {
        //TODO: implement
        throw new NotImplementedException("Export not implemented yet");
    }

    /**
     * The method ''addRecipe(ActionEvent event)'' opens the addRecipe-Popover after a interaction with the plus-button.
     * @param event
     */
    @FXML
    void addRecipe(ActionEvent event) {
        Node node = null;
        try {
            // The fxml can't load the same fxml twice with the same instance but setRoot is a workaround
            // http://stackoverflow.com/questions/21424843/exception-has-occuredroot-value-already-specified-in-javafx-when-loading-fxml-p
            fxmlLoader.setRoot(null);
            fxmlLoader.setController(null);
            fxmlLoader.setLocation( getClass().getResource(Resources.getloadRecipePopOverFXML()) );
            node = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PopOver popOver = new PopOver(node);
        popOver.setDetachable(false);
        popOver.setCornerRadius(4);
        popOver.setArrowLocation(PopOver.ArrowLocation.BOTTOM_LEFT);
        popOver.show(this.plusButton);
    }

    public ListView<Recipe> getListViewRecipes() {
        return listViewRecipes;
    }
}
