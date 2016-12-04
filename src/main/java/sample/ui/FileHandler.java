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
import java.util.prefs.Preferences;


public class FileHandler {

    public static final FileChooser.ExtensionFilter SUPPORTED_EXTENSIONS = new FileChooser.ExtensionFilter("*.PNG", "*.png", "*.JPG", "*.jpg");
    private final String LAST_USED_FOLDER = "";


    private void setIntialPath(File file) {
        Preferences prefs = Preferences.userRoot().node(getClass().getName());
        prefs.put(LAST_USED_FOLDER, file.getParent());
    }

    private File getInitalPath() {
        Preferences prefs = Preferences.userRoot().node(getClass().getName());
        return new File(prefs.get(LAST_USED_FOLDER, new File(".").getAbsolutePath()));
    }

    /**
     * These method imports the data of a single folder.
     */
    File importFolder() {
        Stage stage = new Stage();
        stage.setTitle(Resources.getFileChooserWindowText());
        final DirectoryChooser chooser = new DirectoryChooser();
        chooser.setInitialDirectory(getInitalPath());
        File selectedDirectory = chooser.showDialog(stage);
        setIntialPath(selectedDirectory);
        return selectedDirectory;
    }

    /**
     * These method imports a single or multiple files.
     */
    List<File> importFiles(){
        Stage stage = new Stage();
        stage.setTitle(Resources.getFileChooserWindowText());
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setSelectedExtensionFilter(SUPPORTED_EXTENSIONS);
        fileChooser.setInitialDirectory(getInitalPath());
        List<File> files =
            fileChooser.showOpenMultipleDialog(stage);
        setIntialPath(files.get(0));
        return files;
    }

    /**
     * These method imports a single file.
     */
    File importPicture(){
        Stage stage = new Stage();
        stage.setTitle(Resources.getFileChooserWindowText());
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setSelectedExtensionFilter(SUPPORTED_EXTENSIONS);
        fileChooser.setInitialDirectory(getInitalPath());
        File file = fileChooser.showOpenDialog(stage);
        setIntialPath(file);

        return file;
    }

    /**
     * These method exports a single file.
     */
    File exportFile(){
        Stage stage = new Stage();
        stage.setTitle(Resources.getFileChooserWindowText());
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(getInitalPath());
        File file = fileChooser.showSaveDialog(stage);
        setIntialPath(file);
        return file;
    }
}
