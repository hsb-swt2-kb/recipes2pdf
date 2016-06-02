package sample.ui;

/**
 * @author Tobias Stelter
 * The Class ''FileHandler'' provides methods for opening a filechooser.
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
    File[] importFolder() {
        Stage stage = new Stage();
        stage.setTitle(Resources.getFileChooserWindowText());
        final DirectoryChooser chooser = new DirectoryChooser();
        //File defaultDirectory = new File("c:/dev/javafx");
        //chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(stage);
        return selectedDirectory.listFiles();
    }


    /**
     * These method imports a single or multiple files.
     */
    List<File> importFiles(){
        Stage stage = new Stage();
        stage.setTitle(Resources.getFileChooserWindowText());
        final FileChooser fileChooser = new FileChooser();
        //The user only can choose the formats txt and html.
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.txt", "*.TXT","*.html", "*.HTML");
        fileChooser.setSelectedExtensionFilter(extFilter);
        List<File> files =
            fileChooser.showOpenMultipleDialog(stage);

        return files;
    }

    /**
     * These method imports a single file.
     */
    File importPicture(){
        Stage stage = new Stage();
        stage.setTitle(Resources.getFileChooserWindowText());
        final FileChooser fileChooser = new FileChooser();
        //The user only can choose the formats png and jpg.
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("*.PNG", "*.png","*.JPG","*.jpg");
        fileChooser.setSelectedExtensionFilter(extFilter);
        File file = fileChooser.showOpenDialog(stage);

        return file;

    }

    /**
     * These method exports a single file.
     */
    File exportFile(){
        Stage stage = new Stage();
        stage.setTitle(Resources.getFileChooserWindowText());
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(stage);

        return file;
    }
}
