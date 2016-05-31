package sample.database;

/**
 * Database connection details.
 * Members of this class are public for purpose.
 * Created by czoeller on 26.03.16.
 */
public class DatabaseConnection {
    /**
     * JDBC connector identifier
     */
    public String CONNECTOR;
    /**
     * Path to the database
     */
    public String PATH;
    /**
     * Username for authentication.
     */
    public String USER;
    /**
     * Password for authentication.
     */
    public String PASSWORD;

    private DatabaseConnection() {
<<<<<<< HEAD
        final String path = System.getProperty("user.dir") + "/database.db";
=======
        final DatabaseConfig databaseConfig = new DatabaseConfig(IConfig.getInstance());
>>>>>>> master
        this.CONNECTOR = "org.sqlite.JDBC";
        this.PATH = "jdbc:sqlite://" + path;
        this.USER = null;
        this.PASSWORD = null;
    }

    /**
     * Return default database connection.
     *
     * @return default database connection.
     */
    public static DatabaseConnection getDatabaseConnection() {
        return new DatabaseConnection();
    }

    /**
     * Return sandbox database connection.
     * This is a seperate database for unit/integration-tests.
     *
     * @return sandbox database connection.
     */
    public static DatabaseConnection getSandboxDatabaseConnection() {
        DatabaseConnection sanboxDatabaseConnection = new DatabaseConnection();
        sanboxDatabaseConnection.PATH = sanboxDatabaseConnection.PATH.replace(".db", "-test.db");
        return sanboxDatabaseConnection;
    }

}
