package sample.database;

import sample.config.IConfig;

/**
 * Created by czoeller on 26.03.16.
 */
public class DatabaseConnection {

    public String CONNECTOR;
    public String PATH;
    public String USER;
    public String PASSWORD;

    private DatabaseConnection() {
        final DatabaseConfig databaseConfig = new DatabaseConfig( IConfig.getInstance() );
        this.CONNECTOR = "org.sqlite.JDBC";
        this.PATH = "jdbc:sqlite://" + databaseConfig.getDatabasePath();
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
