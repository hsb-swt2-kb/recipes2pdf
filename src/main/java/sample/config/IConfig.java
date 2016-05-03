package sample.config;

import java.io.File;
import java.util.Properties;

/**
 * Created by kai on 11.04.16.
 */
public interface IConfig {

    static IConfig getInstance() {
        return Config.getInstance();
    }

    String getProperty(String key) throws IllegalArgumentException;

    Properties getProperies();
}
