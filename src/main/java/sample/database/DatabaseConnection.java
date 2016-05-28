package sample.database;

import java.io.File;

/**
 * Created by czoeller on 26.03.16.
 */
public class DatabaseConnection {

    public String CONNECTOR;
    public String PATH;
    public String USER;
    public String PASSWORD;

    private DatabaseConnection() {
        final String path = System.getProperty("user.dir") + File.separator + "database.db";
        this.CONNECTOR = "org.sqlite.JDBC";
        this.PATH = "jdbc:sqlite://" + path;
        this.USER = null;
        this.PASSWORD = null;
    }

    public static DatabaseConnection getDatabaseConnection() {
        return new DatabaseConnection();
    }

    public static DatabaseConnection getSandboxDatabaseConnection() {
        DatabaseConnection sanboxDatabaseConnection = new DatabaseConnection();
        sanboxDatabaseConnection.PATH = sanboxDatabaseConnection.PATH.replace(".db", "-test.db");
        return sanboxDatabaseConnection;
    }

}
