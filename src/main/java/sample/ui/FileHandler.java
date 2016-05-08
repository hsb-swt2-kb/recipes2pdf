package sample.ui;

/**
 * @author Tobias Stelter
 */


import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;


public class FileHandler {

    private final String windowText = "File Chooser";
    private Stage stage = new Stage();

    /**
     * These method imports the data of a single folder.
     */
    void importFolder(){
        stage.setTitle(windowText);
        final DirectoryChooser chooser = new DirectoryChooser();
        //File defaultDirectory = new File("c:/dev/javafx");
        //chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(stage);
    }


    /**
     * These method imports a single or multiple files.
     */
    void importFiles(){
        stage.setTitle(windowText);
        final FileChooser fileChooser = new FileChooser();
        //FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.stl", "*.STL");
        //fileChooser.setSelectedExtensionFilter(extFilter);
        List<File> files =
            fileChooser.showOpenMultipleDialog(stage);
    }

    /**
     * These method imports a single file.
     */
    void importFile(){
       stage.setTitle(windowText);
        final FileChooser fileChooser = new FileChooser();
             //FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.stl", "*.STL");
        //fileChooser.setSelectedExtensionFilter(extFilter);
        File file = fileChooser.showOpenDialog(stage);

    }
}
