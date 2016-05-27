package sample.config;

/**
 * Created by kai on 11.04.16.
 */
public interface IConfig {

    static IConfig getInstance() {
        return Config.getInstance();
    }

    String getProperty(String key) throws IllegalArgumentException;

    void setProperty(String key, String value) throws IllegalArgumentException;
}
