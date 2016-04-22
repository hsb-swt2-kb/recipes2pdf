package sample.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Utils
 * Created by czoeller on 22.04.2016.
 */
public class ResourceLoader {

    /**
     * Loads resource within resource package
     *
     * @param clazz               The class to use loader from.
     * @param packageAbsolutePath The path to the resource absolute to package root. Example: /{package}/{resource.*}
     * @return The string content of the Resource.
     */
    public static String loadFileContents(Class clazz, String packageAbsolutePath) {
        String content = "";
        if (!packageAbsolutePath.startsWith("/")) {
            throw new IllegalArgumentException("Argument must have leading slash followed by package. /{package}/{resource.*}");
        }
        final String resourcePath = clazz.getResource(packageAbsolutePath).getFile();
        final File file = new File(resourcePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("Could not find resource. Please check the path " + packageAbsolutePath);
        }
        final Path path = Paths.get(file.getAbsolutePath());
        try {
            content = new String(Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
