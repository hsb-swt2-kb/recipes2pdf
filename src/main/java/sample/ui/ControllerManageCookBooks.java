package sample.ui;

/**
 * @author Tobias Stelter
 * The Class ''ControllerManageCookBooks'' manages the ManageCookBooks-FXML.
 * It displays all cookbooks and provides methods for adding, changing and deleting a CookBook.
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


    private static ControllerManageCookBooks instance;
    protected String selectedItem;
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

    /**
     * The method ''getInstance'' returns the controllerInstance for passing data beetween the ControllerManageCookBooks and ControllerChangeCookBook.
     *
     * @return controllerInstance
     */

    public static ControllerManageCookBooks getInstance() {

        if (ControllerManageCookBooks.instance == null) {
            synchronized (ControllerManageCookBooks.class) {
                if (ControllerManageCookBooks.instance == null) {
                    ControllerManageCookBooks.instance = new ControllerManageCookBooks();
                }
            }
        }
        return ControllerManageCookBooks.instance;
    }

    protected String getSelectedItem() {
        return this.selectedItem;
    }

    @FXML
    private void initialize() {
        instance = this;
        initializeListeners();
        /* TESTDATA */
        this.cookbooks = FXCollections.observableArrayList("Tobias Kochbuch", "Henriks Kochbuch", "Florians Kochbuch", "Danys Kochbuch", "Christians Kochbuch", "Markuss Kochbuch", "Kais Kochbuch");
         /* TESTDATA END */
        refreshListViews(cookbooks);
    }

    /**
     * The method ''initializeListeners()'' initializes the listeners.
     */

    private void initializeListeners() {
        deleteButton.setOnAction((ActionEvent event) -> {
            String recipe = listViewCookBooks.getSelectionModel().getSelectedItem();
            this.selectedItem = recipe;
            if (recipe != null) {
                controllerDefault.newWindowNotResizable(Resources.getDeleteFXML(), Resources.getDeleteWindowText());
            } else {
                controllerDefault.newWindowNotResizable(Resources.getNoElementsSelectedFXML(), Resources.getErrorWindowText());
            }
        });

        changeButton.setOnAction((ActionEvent event) -> {
            String recipe = listViewCookBooks.getSelectionModel().getSelectedItem();
            this.selectedItem = recipe;
            if (recipe != null) {
                controllerDefault.newWindow(Resources.getChangeCookBooksFXML(), Resources.getChangeCookBookWindowText(), 370, 245, Resources.getDefaultIcon());
            } else {
                controllerDefault.newWindowNotResizable(Resources.getNoElementsSelectedFXML(), Resources.getErrorWindowText());
            }
        });
    }

    /**
     * The method ''refreshListView(ObservableList<String> cookbooks)'' refreshs the listView.
     */

    private void refreshListViews(ObservableList<String> cookbooks) {
        FXCollections.sort(cookbooks);
        this.listViewCookBooks.setItems(cookbooks);
        ControllerManageCookBook controllerManageCookBook = new ControllerManageCookBook();
        controllerManageCookBook.searchInListView(cookbooks, searchFieldCookBooks, listViewCookBooks);
    }

    /**
     * The method ''addCookBook(ActionEvent event)'' opens the addCookBook-window.
     *
     * @param event
     */

    @FXML
    void addCookBook(ActionEvent event) {
        controllerDefault.newWindow(Resources.getAddCookBookFXML(), Resources.getAddCookBookWindowText(), 370, 245, Resources.getDefaultIcon());

    }

    /**
     * The method ''closeWindow()'' closes the manage-cookbooks-window after a interaction with the close-button.
     *
     * @param event
     */

    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();


    }

    @FXML
    void changeCookBook(ActionEvent event) {

    }

}
