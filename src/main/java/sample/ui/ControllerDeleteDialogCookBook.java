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
import sample.exceptions.CookBookNotFoundException;
import sample.model.Cookbook;

public class ControllerDeleteDialogCookBook {

    @FXML
    private Button closeButton;
    @FXML
    private Button deleteButton;

    @FXML
    void deleteElement(ActionEvent event) {
        Cookbook cookbook;
        try{
            cookbook = UI.searchCookBook(ControllerManageCookBooks.getInstance().getSelectedItem());
            UI.delCookBook(cookbook);
            ControllerManageCookBooks.getInstance().refreshListViews();
            ControllerManageCookBook.getInstance().refresh();
        }
        catch(CookBookNotFoundException e){ System.out.println("Couldn't load cookbook");}

        Stage stage = (Stage) deleteButton.getScene().getWindow();
        stage.close();
    }

    /**
     * The method ''closeDeleteDialog()'' closes the delete-dialog after a interaction with the close-button.
     * @param event event this method was effected by
     */
    @FXML
    void closeDeleteDialog(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
