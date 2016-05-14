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
    private final String noElementSelectedFXML = "/sample/ui/NoElementSelectedDialog.fxml";
    private final String noElementSelectedTEXT = "Error";
    private final String addCookBookFXML = "/sample/ui/AddCookBook.fxml";
    private final String addCookBookWindowTitel = "Kochbuch hinzufügen";
    ControllerDefault controllerDefault = new ControllerDefault();
    @FXML
    private Button closeButton;
    @FXML
    private TextField searchFieldCookBooks;
    @FXML
    private ListView<String> listViewCookBooks;
    @FXML
    private Button changeButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button addButton;
    private ObservableList<String> cookbooks;

    @FXML
    private void initialize() {
        initializeListeners();
        /* TESTDATA */
        this.cookbooks = FXCollections.observableArrayList("Tobias Kochbuch", "Henriks Kochbuch", "Florians Kochbuch", "Danys Kochbuch", "Christians Kochbuch", "Markuss Kochbuch", "Kais Kochbuch");
         /* TESTDATA END */
        refreshListViews(cookbooks);
    }

    private void initializeListeners() {
        deleteButton.setOnAction((ActionEvent event) -> {
            String recipe = listViewCookBooks.getSelectionModel().getSelectedItem();
            if (recipe != null) {
                // DO
            } else {
                controllerDefault.newWindowNotResizable(noElementSelectedFXML, noElementSelectedTEXT);
            }
        });

        changeButton.setOnAction((ActionEvent event) -> {
            String recipe = listViewCookBooks.getSelectionModel().getSelectedItem();
            if (recipe != null) {
                controllerDefault.newWindow(changeCookBookFXML, changeCookBookText, 370, 245, defaultIconPath);
            } else {
                controllerDefault.newWindowNotResizable(noElementSelectedFXML, noElementSelectedTEXT);
            }
        });
    }

    private void refreshListViews(ObservableList<String> cookbooks) {
        FXCollections.sort(cookbooks);
        this.listViewCookBooks.setItems(cookbooks);
        ControllerManageCookBook controllerManageCookBook = new ControllerManageCookBook();
        controllerManageCookBook.searchInListView(cookbooks, searchFieldCookBooks, listViewCookBooks);
    }

    @FXML
    void addCookBook(ActionEvent event) {
        controllerDefault.newWindow(addCookBookFXML, addCookBookWindowTitel, 370, 245, defaultIconPath);

    }


    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();


    }

}
