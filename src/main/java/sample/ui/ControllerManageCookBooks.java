package sample.ui;

/**
 * @author Tobias Stelter
 */


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControllerManageCookBooks {


    private final String changeCookBookFXML = "/sample/ui/ChangeCookBook.fxml";
    private final String defaultIconPath = "/sample/ui/icon_bg_small.png";
    private final String changeCookBookText = "Kochbuch ändern";

    @FXML
    private Button closeButton;

    @FXML
    private TextField searchFieldCookBooks;


    @FXML
    private ListView<String> listViewCookBooks;


    @FXML
    private Button changeButton;

    private ObservableList<String> cookbooks;

    @FXML
    private void initialize() {
        /* TESTDATA */
        this.cookbooks = FXCollections.observableArrayList("Tobias Kochbuch", "Henriks Kochbuch", "Florians Kochbuch", "Danys Kochbuch", "Christians Kochbuch", "Markuss Kochbuch", "Kais Kochbuch");
         /* TESTDATA END */
        refreshListViews(cookbooks);
    }

    private void refreshListViews(ObservableList<String> cookbooks) {
        FXCollections.sort(cookbooks);
        this.listViewCookBooks.setItems(cookbooks);
        ControllerManageCookBook controllerManageCookBook = new ControllerManageCookBook();
        controllerManageCookBook.searchInListView(cookbooks, searchFieldCookBooks, listViewCookBooks);
    }

    @FXML
    void changeCookBook(ActionEvent event) {
        ControllerDefault controllerDefault = new ControllerDefault();
        controllerDefault.newWindow(changeCookBookFXML, changeCookBookText, 370, 245, defaultIconPath);

    }

    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();


    }

}
