package sample.ui;

/**
 * @author Tobias Stelter
 */


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.File;

public class ControllerChangeCookBook {

    @FXML
    private Button fileChooserButton;


    File file;
    String name;
    String foreword;

        @FXML
        private Button closeButton;

    @FXML
    private TextField textFieldName;

    @FXML
    private TextArea textAreaVorwort;

    @FXML
    private TextField textFieldPicture;

    @FXML
    private Button changeButton;

    @FXML
    void changeCookBook(ActionEvent event) {
        getName();
        getForeWord();
        if(this.name != null) {
            System.out.println(name + foreword + "file.getName()");
            //Close Stage
            Stage stage = (Stage) changeButton.getScene().getWindow();
            stage.close();
        }else{
            //Exception
        }

    }

        @FXML
        void closeChangeCookBook(ActionEvent event) {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();

        }

    @FXML
    void openFileChooser(ActionEvent event) {
        FileHandler fileHandler = new FileHandler();
        this.file = fileHandler.importPicture();
        if(this.file != null) {
            textFieldPicture.setText(file.getAbsolutePath());
        }

    }

    private String getForeWord(){
        foreword = textAreaVorwort.getText();
        return foreword;
    }
    private String getName(){
        name = textFieldName.getText();
        return  name;
    }

    }


