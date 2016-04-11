package sample.config;

import java.io.BufferedReader;
import java.util.Properties;

/**
 * Created by kai on 11.04.16.
 */
class Configuration implements IConfiguration {

    private static Configuration config = new Configuration();
    private Properties properties;


    private Configuration() {

        BufferedReader reader;
        properties = new Properties();

//        try {
        setProperties();
//            reader = new BufferedReader(new FileReader(new File(CONFIG_FILE_PATH)));
//            properties.load(reader);
//            reader.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static Configuration getInstance() {
        return config;
    }

    private void setProperties() {
        properties.setProperty("USER_DIR", "/home/kai/.recipes2pdf");
        properties.setProperty("WORKKING_DIRECTORY", "PdfBuilder");
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
