package sample.ui;

/**
 * @author Tobias Stelter
 * The Class ''ControllerDeleteDialogCookBook'' manages the DeleteDialog-FXML.
 * It displays the delete-dialog.
 */


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.exceptions.RecipeNotFoundException;
import sample.model.Recipe;

public class ControllerDeleteDialogRecipe {

    @FXML
    private Button closeButton;
    @FXML
    private Button deleteButton;

    @FXML
    void deleteElement(ActionEvent event) {
        ObservableList<String> selectedItems = ControllerManageCookBook.getInstance().getSelectedRecipes();
        Recipe recipe;
        try{
            for(String recipeName:selectedItems){
                recipe= UI.searchRecipe(recipeName);
                UI.delRecipe(recipe);
                ControllerManageCookBook.getInstance().refresh();
            }
        }
        catch(RecipeNotFoundException e){
            System.out.println("Couldn't load recipe");
        }

        Stage stage = (Stage) deleteButton.getScene().getWindow();
        stage.close();
    }

    /**
     * The method ''closeDeleteDialog()'' closes the delete-dialog after a interaction with the close-button.
     *
     * @param event
     */
    @FXML
    void closeDeleteDialog(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
