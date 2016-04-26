package sample.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by kai on 11.04.16.
 */
class Config implements IConfig {

    private static Config config = new Config();
    private Properties properties;


    private Config() {

        properties = new Properties();

        setProperties();
        try {
            properties.store(new FileOutputStream(IConfig.PROGRAM_USERDATA_DIR + File.separator + "config.txt"), "Properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Config getInstance() {
        return config;
    }

    private void setProperties() {

        properties.setProperty("TEMP_FOLDER_NAME", "temp");
        properties.setProperty("IMAGE_FOLDER_NAME", "images");
        properties.setProperty("TEMPLATE_FOLDER_NAME", "templates");
        properties.setProperty("TEMPLATE_FILE_NAME", "cookbookTemplate.tex");
        properties.setProperty("OUTPUT_FOLDER_NAME", "output");
        properties.setProperty("OUTPUT_FILE_PREFIX", "recipe");
        properties.setProperty("IMAGE_PREFIX", "image_");
    }


    public String getProperty(String key) throws IllegalArgumentException {
        if (!properties.containsKey(key)) {
            throw new IllegalArgumentException("Property " + key + "not found!");
        }
        return properties.getProperty(key);
    }

    public String getProperyList() {
        return properties.toString();
    }
}
