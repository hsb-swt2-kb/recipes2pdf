package sample.ui;

/**
 * @author Tobias Stelter
 * The Class ''ControllerDeleteDialogCookBook'' manages the DeleteDialog-FXML.
 * It displays the delete-dialog.
 */


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.model.Cookbook;

import javax.inject.Inject;

public class ControllerDeleteDialogCookBook {

    @FXML
    private Button closeButton;
    @FXML
    private Button deleteButton;
    @Inject
    UI ui;

    @Inject
    private ControllerManageCookBook controllerManageCookBook;

    @Inject
    private ControllerManageCookBooks controllerManageCookBooks;

    @FXML
    void deleteElement(ActionEvent event) {
        Cookbook cookbook = controllerManageCookBooks.getListViewCookBooks().getSelectionModel().getSelectedItem();
        ui.delCookBook(cookbook);
        controllerManageCookBooks.refreshListViews();
        //TODO: maybe refresh ControllerManageCookBook.getInstance().refresh();

        Stage stage = (Stage) deleteButton.getScene().getWindow();
        stage.close();
    }

    /**
     * The method ''closeDeleteDialog()'' closes the delete-dialog after a interaction with the close-button.
     * @param event
     */
    @FXML
    void closeDeleteDialog(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
