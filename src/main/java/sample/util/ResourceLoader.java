package sample.util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Utils
 * Created by czoeller on 22.04.2016.
 */
public class ResourceLoader {

    /**
     * Loads resource within resource package
     *
     * @param clazz               The class to use loader from.
     * @param packageAbsolutePath The path to the resource relative to passed clazz or absolute to package root.
     *                            Example: {resource.*}
     *                            Example: /sample/{package}/{resource.*}
     * @return The string content of the Resource.
     */
    public static <T> String loadFileContents(Class<T> clazz, String packageAbsolutePath) {
        String content = "";
        final InputStream is = clazz.getResourceAsStream(packageAbsolutePath);
        try {
            content = IOUtils.toString(is, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
        }
        return content;
    }
}
