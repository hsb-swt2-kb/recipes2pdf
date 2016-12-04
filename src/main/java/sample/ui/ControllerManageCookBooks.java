package sample.ui;

/**
 * @author Tobias Stelter
 * The Class ''ControllerManageCookBooks'' manages the ManageCookBooks-FXML.
 * It displays all cookbooks and provides methods for adding, changing and deleting a CookBook.
 */


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.fxmisc.easybind.EasyBind;
import sample.model.Cookbook;
import sample.ui.adapter.CookbookAdapter;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;

public class ControllerManageCookBooks {

    private static ControllerManageCookBooks instance;
    protected Cookbook selectedItem;
    ControllerDefault controllerDefault = new ControllerDefault();
    private ObservableList<Cookbook> cookbooks;

    @FXML
    private Button closeButton;
    @FXML
    private TextField searchFieldCookBooks;
    @FXML
    private ListView<Cookbook> listViewCookBooks;
    @FXML
    private Button changeButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button addButton;
    @Inject
    UI ui;
    @Inject
    ControllerError controllerError;

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

    protected Cookbook getSelectedItem() {
        return this.selectedItem;
    }

    @FXML
    private void initialize() {
        instance = this;
        initializeListeners();
        loadInfo();
        refreshListViews();
        setupSearchFilter();
    }

    private void manageSaveError(String boldPrint, String littlePrint) {
        controllerError.setLabels(boldPrint, littlePrint);
    }

    /**
     * The method ''initializeListeners()'' initializes the listeners.
     */
    private void initializeListeners() {
        setupMultipleSelection();
        doubleClick();
        buttonActions();
    }

    private void setupSearchFilter() {
        FilteredList<Cookbook> filteredData = new FilteredList<>(this.listViewCookBooks.getItems(), p -> true);
        this.searchFieldCookBooks.textProperty()
                                 .addListener((observable, oldValue, newValue) -> {
                                     filteredData.setPredicate(newValue.isEmpty() ? s -> true : s -> s.getTitle().toLowerCase()
                                                                                                      .contains(newValue.toLowerCase()));
                                 });
        this.listViewCookBooks.setItems(filteredData);
    }

    private void buttonActions() {
        deleteButton.setOnAction((ActionEvent event) -> {
            Cookbook cookbook = listViewCookBooks.getSelectionModel().getSelectedItem();
            this.selectedItem = cookbook;
            if (cookbook != null) {
                controllerDefault.newWindowNotResizable(Resources.getDeleteCookBookFXML(), Resources.getDeleteWindowText());
            } else {
                manageSaveError("Sie haben kein Element ausgwählt.", "Bitte wählen Sie ein Kochbuch aus.");
            }
        });
        changeButton.setOnAction((ActionEvent event) -> {
            Cookbook cookbook = listViewCookBooks.getSelectionModel().getSelectedItem();
            this.selectedItem = cookbook;
            if (cookbook != null) {
                controllerDefault.newWindow(Resources.getChangeCookBooksFXML(), Resources.getChangeCookBookWindowText(), 370, 245, Resources.getDefaultIcon());
            } else {
                manageSaveError("Sie haben kein Element ausgwählt.", "Bitte wählen Sie ein Kochbuch aus.");
            }
        });
    }

    /**
     * The method ''refreshListView(ObservableList<Cookbook> cookbooks)'' refreshs the listView.
     */
    protected void refreshListViews() {
        loadInfo();
        if(this.listViewCookBooks != null) {
            FXCollections.sort(this.cookbooks, Comparator.comparing(Cookbook::getTitle));
            this.listViewCookBooks.getItems().clear();
            this.listViewCookBooks.setCellFactory(new CookbookAdapter());
            this.listViewCookBooks.setItems(this.cookbooks);
            ControllerManageCookBook controllerManageCookBook = new ControllerManageCookBook();
            //TODO: controllerManageCookBook.searchInListView(this.cookbooks, searchFieldCookBooks, listViewCookBooks);
        }
    }

    void loadInfo(){
        this.cookbooks = FXCollections.observableArrayList();
        List<Cookbook> cookbooksDB = ui.getAllCookbooksFromDB();
        ObservableList<Cookbook> cookbooksObservable = FXCollections.observableArrayList( cookbooksDB);
        EasyBind.listBind(this.cookbooks, cookbooksObservable);
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
                if (click.getClickCount() >= 1) {
                    selectedItem = listViewCookBooks.getSelectionModel().getSelectedItem();
                }
                if ((!listViewCookBooks.getItems().isEmpty()) && (selectedItem != null)) {
                    if (click.getClickCount() == 2) {
                        controllerDefault.newWindowNotResizable(Resources.getChangeCookBooksFXML(), Resources.getChangeCookBookWindowText());
                    }
                }
            }
        });
    }

    /**
     * Setup multiple selection for listviews.
     */
    private void setupMultipleSelection() {
        listViewCookBooks.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
