package sample.config;

/**
 * Interface for global configuration.
 */
public interface IConfig {

    static IConfig getInstance() {
        return Config.getInstance();
    }
    String getProperty(String key) throws IllegalArgumentException;
    void setProperty(String key, String value) throws IllegalArgumentException;
}
