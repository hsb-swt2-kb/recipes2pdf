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
import sample.model.Recipe;

import javax.inject.Inject;

public class ControllerDeleteDialogRecipe {

    @FXML
    private Button closeButton;
    @FXML
    private Button deleteButton;
    @Inject
    private ControllerManageCookBook controllerManageCookBook;
    @Inject
    private UI ui;

    @FXML
    void deleteElement(ActionEvent event) {

        ObservableList<Recipe> selectedItems = controllerManageCookBook.getListViewRecipes().getSelectionModel().getSelectedItems();
        for (Recipe recipe : selectedItems) {
            ui.delRecipe(recipe);
            controllerManageCookBook.getListViewRecipes().getItems().remove(recipe);
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
