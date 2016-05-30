package sample.ui;

/**
 * @author Tobias Stelter
 * The Class ''ControllerManageCookBooks'' manages the ManageCookBooks-FXML.
 * It displays all cookbooks and provides methods for adding, changing and deleting a CookBook.
 */


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.model.Cookbook;

import java.util.List;

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
        loadInfo();
        refreshListViews();
    }

    /**
     * The method ''initializeListeners()'' initializes the listeners.
     */

    private void initializeListeners() {
        doubleClick();

        deleteButton.setOnAction((ActionEvent event) -> {
            String cookbook = listViewCookBooks.getSelectionModel().getSelectedItem();
            this.selectedItem = cookbook;
            if (cookbook != null) {
                controllerDefault.newWindowNotResizable(Resources.getDeleteCookBookFXML(), Resources.getDeleteWindowText());
            } else {
                controllerDefault.newWindowNotResizable(Resources.getNoElementsSelectedFXML(), Resources.getErrorWindowText());
            }
        });

        changeButton.setOnAction((ActionEvent event) -> {
            String cookbook = listViewCookBooks.getSelectionModel().getSelectedItem();
            this.selectedItem = cookbook;
            if (cookbook != null) {
                controllerDefault.newWindow(Resources.getChangeCookBooksFXML(), Resources.getChangeCookBookWindowText(), 370, 245, Resources.getDefaultIcon());
            } else {
                controllerDefault.newWindowNotResizable(Resources.getNoElementsSelectedFXML(), Resources.getErrorWindowText());
            }
        });
    }

    /**
     * The method ''refreshListView(ObservableList<String> cookbooks)'' refreshs the listView.
     */

    protected void refreshListViews() {
        loadInfo();
        FXCollections.sort(this.cookbooks);
        this.listViewCookBooks.getItems().clear();
        this.listViewCookBooks.setItems(this.cookbooks);
        ControllerManageCookBook controllerManageCookBook = new ControllerManageCookBook();
        controllerManageCookBook.searchInListView(this.cookbooks, searchFieldCookBooks, listViewCookBooks);
    }

    void loadInfo(){
        this.cookbooks = FXCollections.observableArrayList();
        List<Cookbook> cookbooksDB = UI.getAllCookbooksFromDB();
        for (Cookbook cookbook : cookbooksDB) {
            this.cookbooks.add(cookbook.getTitle());
        }

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

    private void doubleClick() {
        listViewCookBooks.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent click) {

                String cookbook = listViewCookBooks.getSelectionModel().getSelectedItem();
                selectedItem = cookbook;

                if (click.getClickCount() == 2) {
                    controllerDefault.newWindowNotResizable(Resources.getChangeCookBooksFXML(), Resources.getChangeCookBookWindowText());

                }
            }
        });
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
