package sample.GUI;

import javafx.fxml.FXML;
import javafx.event.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class ControllerDefault {



        @FXML
        private MenuItem endMenuItem;

        @FXML
        private MenuItem addCookBook;

        @FXML
        private MenuItem addReceipe;

        @FXML
        private MenuItem manageReceipe;

        @FXML
        private MenuItem manageCookBook;

        @FXML
        private MenuItem openHelp;

        @FXML
        private AnchorPane content;

        @FXML
        private MenuItem openAbout;

        void changeLayout(String fxml) throws Exception {
        //change content through new FXML-File
        AnchorPane myPane = (AnchorPane)FXMLLoader.load(getClass().getResource(fxml));
        content.getChildren().clear();
        //Set Constrains Back to the defaults
        content.setTopAnchor(myPane, 25.0); content.setBottomAnchor(myPane, 2.0); content.setRightAnchor(myPane, 0.0); content.setLeftAnchor(myPane, 0.0);
        content.getChildren().add(myPane);
         }

        @FXML
        void addCookBook(ActionEvent event) throws Exception {
        //Pane (Content) durch anderes Pane in anderer FXML ersetzten
        changeLayout("/sample/GUI/AddCookBook.fxml");
         }

        @FXML
        void endProgramm(ActionEvent event) {
                System.exit(1);
        }

        @FXML
        void manageCookBook(ActionEvent event) {

        }

        @FXML
        void addReceipe(ActionEvent event) throws Exception {
            //Pane (Content) durch anderes Pane in anderer FXML ersetzten
            changeLayout("/sample/GUI/AddRecipe.fxml");
        }

        @FXML
        void manageReceipe(ActionEvent event) {

        }

        @FXML
        void openHelp(ActionEvent event) {

        }

        @FXML
        void openAbout(ActionEvent event) throws Exception{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/GUI/About.fxml"));
                Parent root1 = (Parent) loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();        }

    }







