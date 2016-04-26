package sample.config;

import java.io.File;

/**
 * Created by kai on 11.04.16.
 */
public interface IConfig {

    String PROGRAM_USERDATA_DIR = System.getProperty("user.home") + File.separator + ".recipes2pdf";

    static IConfig getInstance() {
        return Config.getInstance();
    }

    String getProperty(String key) throws IllegalArgumentException;

    String getProperyList();
}
