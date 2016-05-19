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

    /**
     * These method imports the data of a single folder.
     */
    void importFolder(){
        Stage stage = new Stage();
        stage.setTitle(Resources.getFileChooserWindowText());
        final DirectoryChooser chooser = new DirectoryChooser();
        //File defaultDirectory = new File("c:/dev/javafx");
        //chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(stage);
    }


    /**
     * These method imports a single or multiple files.
     */
    void importFiles(){
        Stage stage = new Stage();
        stage.setTitle(Resources.getFileChooserWindowText());
        final FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.txt", "*.TXT","*.html", "*.HTML");
        fileChooser.setSelectedExtensionFilter(extFilter);
        List<File> files =
            fileChooser.showOpenMultipleDialog(stage);
    }

    /**
     * These method imports a single file.
     */
    void importFile(){
        Stage stage = new Stage();
        stage.setTitle(Resources.getFileChooserWindowText());
        final FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.txt", "*.TXT","*.html", "*.HTML");
        fileChooser.setSelectedExtensionFilter(extFilter);
        File file = fileChooser.showOpenDialog(stage);

    }
}
