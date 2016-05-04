package sample.ui;

/**
 * Created by Tobias on 02.05.2016.
 */

import java.io.File;
import java.util.List;
import javafx.stage.FileChooser;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;


public class FileHandler {

    private Stage stage  = new Stage();


    void importFolder(){
        stage.setTitle("JavaFX Projects");
        final DirectoryChooser chooser = new DirectoryChooser();
        //File defaultDirectory = new File("c:/dev/javafx");
        //chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(stage);
    }

    void importFiles(){
        stage.setTitle("File Chooser");
        final FileChooser fileChooser = new FileChooser();
        //FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.stl", "*.STL");
        //fileChooser.setSelectedExtensionFilter(extFilter);
        List<File> files =
            fileChooser.showOpenMultipleDialog(stage);
    }

    void importFile(){
       stage.setTitle("File Chooser");
        final FileChooser fileChooser = new FileChooser();
             //FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.stl", "*.STL");
        //fileChooser.setSelectedExtensionFilter(extFilter);
        File file = fileChooser.showOpenDialog(stage);

    }
}
