package sample.database;

/**
 * Created by czoeller on 26.03.16.
 */
public class DatabaseConnection {
    public static String CONNECTOR;
    public static String PATH;
    public static String USER;
    public static String PASSWORD;

    public DatabaseConnection() {
        final String path = System.getProperty("user.dir") + "/database.db";
        this.CONNECTOR = "org.sqlite.JDBC";
        this.PATH = "jdbc:sqlite://" + path;
        this.USER = null;
        this.PASSWORD = null;
    }

}
