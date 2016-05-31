package sample.database;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 * Manages connection to database.
 * Created by czoeller on 30.03.16.
 */
public class Database {

    final Logger LOG = LoggerFactory.getLogger(Database.class);
    /**
     * DatabaseConnection details
     */
    private DatabaseConnection databaseConnection;

    public Database(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
        if (!isInstalled()) {
            try {
                install();
            } catch (SQLException | IOException ex) {
                LOG.error("Database installation failed.", ex);
            }
        }
        openConnection();
    }

    /**
     * Installs the database schema.
     *
     * @throws SQLException
     * @throws IOException
     */
    public void install() throws SQLException, IOException {
        Connection connection = DriverManager.getConnection(databaseConnection.PATH);

        LOG.info("Database connection opened successfully");
        LOG.info("Installing database...");

        Statement statement = connection.createStatement();

        // SQL statement to create tables
        final String sql = new String(Files.readAllBytes(Paths.get( new File(this.getClass().getClassLoader().getResource("sample/database/db.sql").getPath()).getAbsolutePath())));

        // execute the statement string
        statement.executeUpdate(sql);

        LOG.info("Database installed.");

        // close resources
        statement.close();
        connection.close();
    }

    /**
     * Check if database schema already installed.
     *
     * @return true if installed, otherwise false.
     */
    public boolean isInstalled() {
        boolean installed = false;
        openConnection();
        if (Base.hasConnection()) {
            installed = 0 < Base.count("sqlite_master", "type = 'table' AND name != 'sqlite_sequence'");
        }
        closeConnection();
        return installed;
    }

    /**
     * Open connection to database.
     */
    private void openConnection() {
        if (!Base.hasConnection()) {
            Base.open(databaseConnection.CONNECTOR, databaseConnection.PATH, databaseConnection.USER, databaseConnection.PASSWORD);
        }
    }

    /**
     * Close connection to database.
     */
    private void closeConnection() {
        Base.close(true);
    }

    /**
     * Drop all database information.
     */
    public void drop() {
        openConnection();
        final List<Map> all = Base.findAll("SELECT tbl_name FROM sqlite_master WHERE type = 'table' AND tbl_name != 'sqlite_sequence'");
        for (Map<String, String> map : all) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                Base.exec(String.format("DROP table %s", entry.getValue()));
            }
        }
        Base.exec("DELETE FROM sqlite_sequence");
    }
}
