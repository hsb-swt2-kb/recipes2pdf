package sample.database;

import com.github.vbauer.herald.annotation.Log;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

/**
 * Manages connection to database.
 * Created by czoeller on 30.03.16.
 */
public class Database {

    @Log
    private Logger LOG;
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
        InputStream is = getClass().getClassLoader().getResourceAsStream("sample/database/db.sql");
        String sql = IOUtils.toString(is, "UTF-8");
        IOUtils.closeQuietly(is);

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
        try {
            DatabaseMetaData metadata = DriverManager.getConnection(databaseConnection.PATH).getMetaData();

            ResultSet resultSet;
            resultSet = metadata.getTables(null, null, "tablename", null);
            if(resultSet.next())
                installed = true;
            return installed;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return installed;
    }

    /**
     * Drop all database information.
     */
    public void drop() {
        try {
            FileUtils.forceDelete( new File( databaseConnection.PATH ) );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
