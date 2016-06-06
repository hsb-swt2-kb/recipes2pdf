package sample.ui;

/**
 * @author Tobias Stelter
 * The Class ''ControllerDefault'' manages the Default-FXML.
 * It contains the tabs and an anchorPane, which can exchange dynamically through other FXML-files.
 */


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class ControllerDefault {

    private final String wrongPathException = "The FXML-file is wrong or the FXML-file does not exist or there are other problems with the loader.";

    @FXML
    private MenuItem endMenuItem;
    @FXML
    private MenuItem addCookBook;
    @FXML
    private MenuItem addReceipe;
    @FXML
    private MenuItem manageCookBook;
    @FXML
    private MenuItem openDataFromRecipe;
    @FXML
    private MenuItem openDataFromCookBook;
    @FXML
    private MenuItem openHelp;
    @FXML
    private AnchorPane content;
    @FXML
    private MenuItem openAbout;
    @FXML
    private MenuItem data;

    /**
     * These method changes the layout in the main window.
     *
     * @param fxml defenies the path of the new FXML-Layout-File.
     */
    void changeLayout(String fxml) {
        //Pane (Content) durch anderes Pane in anderer FXML ersetzten
        Parent newContent = null;
        try {
            newContent = FXMLLoader.load(getClass().getResource(fxml));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(wrongPathException);
        }

        //Set Constrains Back to the defaults
        AnchorPane.setTopAnchor(newContent, 25.0);
        AnchorPane.setBottomAnchor(newContent, 2.0);
        AnchorPane.setRightAnchor(newContent, 0.0);
        AnchorPane.setLeftAnchor(newContent, 0.0);
        content.getChildren().setAll(newContent);
    }

    /**
     * The method opens a new not resizable window.
     * @param fxml defenies the path of the FXML-File.
     * @param windowTitel defenies the window titel
     */
    void newWindowNotResizable(String fxml, String windowTitel) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(wrongPathException);
        }
        Stage stage = new Stage();
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream(Resources.getDefaultIcon())));
        stage.setScene(new Scene(root));
        stage.setTitle(windowTitel);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Opens a new resizable window.
     * @param fxml defenies the path of the FXML-File.
     * @param windowTitel defenies the window titel
     * @param minValueWidth defenies the min value for the window size
     * @param maxValueHeight defenies the max value for the window size
     */
    void newWindow(String fxml, String windowTitel, double minValueWidth, double maxValueHeight, String pathIcon) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(wrongPathException);
        }
        Stage stage = new Stage();
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream(pathIcon)));
        stage.setScene(new Scene(root));
        stage.setTitle(windowTitel);
        stage.setMinWidth(minValueWidth);
        stage.setMinHeight(maxValueHeight);
        stage.show();
    }

    /**
     * The method ''addCookBook()'' opens the addCookBook-window.
     *
     * @param event
     */
    @FXML
    void addCookBook(ActionEvent event) {
        //Pane (Content) durch anderes Pane in anderer FXML ersetzten
        newWindow(Resources.getAddCookBookFXML(), Resources.getAddCookBookWindowText(), 370, 245, Resources.getDefaultIcon());
    }

    @FXML
    void endProgramm(ActionEvent event) {
        System.exit(1);
    }

    @FXML
    void addReceipe(ActionEvent event) {
        newWindowNotResizable(Resources.getloadRecipeFXML(), Resources.getLoadWindowText());
    }

    /**
     * The method ''openHelp()'' opens the help-window.
     * @param event
     */
    @FXML
    void openHelp(ActionEvent event) {
        newWindow(Resources.getHelpFXML(), Resources.getHelpWindowText(), 250, 200, Resources.getHelpIcon());

    }

    /**
     * The method ''openAbout()'' opens the about-window.
     * @param event
     */
    @FXML
    void openAbout(ActionEvent event) {
        newWindow(Resources.getAboutFXML(), Resources.getAboutWindowText(), 300, 220, Resources.getInfoIcon());
    }

    /**
     * The method ''openManageCookBooks()'' opens the  manage-cookbook-window.
     * @param event
     */
    @FXML
    void openManageCookBooks(ActionEvent event) {
        newWindow(Resources.getMangeCookBooksFXML(), Resources.getManageCookBooksWindowText(), 265, 350, Resources.getDefaultIcon());
    }
}







