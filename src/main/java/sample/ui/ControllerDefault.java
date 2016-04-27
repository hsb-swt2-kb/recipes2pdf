package sample.ui;

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
    private final String aboutFXML = "/sample/ui/About.fxml";
    private final String manageCookBookFXML = "/sample/ui/ManageCookBook.fxml";
    private final String pathIcon = "/sample/ui/icon_bg_small.png";
    private final String addCookBookFXML = "/sample/ui/AddCookBook.fxml";
    private final String helpFXML =  "/sample/ui/Help.fxml";
    private final String addRecipeFXML = "/sample/ui/AddRecipe.fxml";
    private final String aboutWindowTitel = "Über";
    private final String helpWindowTitel = "Hilfe";
    private final String addRecipeWindowTitel = "Rezepte hinzufügen";
    private final String addCookBookWindowTitel = "Kochbuch hinzufügen";

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
        content.setTopAnchor(newContent, 25.0);
        content.setBottomAnchor(newContent, 2.0);
        content.setRightAnchor(newContent, 0.0);
        content.setLeftAnchor(newContent, 0.0);
        content.getChildren().setAll(newContent);
    }

    void newWindow(String fxml, String windowTitel) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = null;
        try {
            root = (Parent) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(wrongPathException);
        }
        Stage stage = new Stage();
        stage.getIcons().add(new Image(this.getClass().getResourceAsStream(pathIcon)));
        stage.setScene(new Scene(root));
        stage.setTitle(windowTitel);
        stage.show();
    }

    @FXML
    void addCookBook(ActionEvent event) {
        //Pane (Content) durch anderes Pane in anderer FXML ersetzten
        newWindow(addCookBookFXML, addCookBookWindowTitel);
    }

    @FXML
    void endProgramm(ActionEvent event) {
        System.exit(1);
    }

    @FXML
    void manageCookBook(ActionEvent event) {
        changeLayout(manageCookBookFXML);
    }

    @FXML
    void addReceipe(ActionEvent event) {
        //Pane (Content) durch anderes Pane in anderer FXML ersetzten
        newWindow(addRecipeFXML, addRecipeWindowTitel);
        changeLayout(manageCookBookFXML);
    }

    @FXML
    void openHelp(ActionEvent event) {
        newWindow(helpFXML, helpWindowTitel);

    }

    @FXML
    void openAbout(ActionEvent event) {
        newWindow(aboutFXML, aboutWindowTitel);
    }

    @FXML
    void openDataFromRecipe(ActionEvent event) {

    }

    @FXML
    void openDataFromCookBook(ActionEvent event) {

    }

}







