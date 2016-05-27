package sample.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by kai on 11.04.16.
 */
class Config implements IConfig {

    private static Config config = new Config();
    private Properties properties;
    final String PROGRAM_USERDATA_DIR = System.getProperty("user.home") + File.separator + ".recipes2pdf";

    private Config() {

        properties = new Properties();
        try {
            properties.load(new FileInputStream(PROGRAM_USERDATA_DIR + File.separator + "config.cfg"));
        } catch (IOException e) {

            try {
                properties.load(this.getClass().getClassLoader().getResourceAsStream("sample/config/default_config.cfg"));
                properties.store(new FileOutputStream(PROGRAM_USERDATA_DIR + File.separator + "config.txt"), "Properties");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static Config getInstance() {
        return config;
    }

    public String getProperty(String key) throws IllegalArgumentException {
        if (key.equalsIgnoreCase("PROGRAM_USERDATA_DIR")){
            return PROGRAM_USERDATA_DIR;
        }
        else if (!properties.containsKey(key)) {
            throw new IllegalArgumentException("Property " + key + "not found!");
        }
        return properties.getProperty(key);
    }

    @Override
    public void setProperty(String key, String value) throws IllegalArgumentException {
        properties.setProperty(key, value);
    }
}
