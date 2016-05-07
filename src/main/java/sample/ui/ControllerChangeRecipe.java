package sample.ui;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ControllerChangeRecipe {

    private final String notEnoughArgumentsFXML = "/sample/ui/NotEnoughArguments.fxml";
    private final String error = "Error";

    @FXML
    private Button fileChooserButton;

        @FXML
        private Button closeButton;

    @FXML
    private Button changeButton;

        @FXML
        void closeChangeRecipe(ActionEvent event) {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();

        }

    @FXML
    void changeRecipe(ActionEvent event) {

        ControllerDefault controllerDefault = new ControllerDefault();
        controllerDefault.newWindowNotResizable(notEnoughArgumentsFXML, error);

        Stage stage = (Stage) changeButton.getScene().getWindow();
        stage.close();

    }

    @FXML
    void openFileChooser(ActionEvent event) {
        FileHandler fileHandler = new FileHandler();
        fileHandler.importFile();

    }


}


