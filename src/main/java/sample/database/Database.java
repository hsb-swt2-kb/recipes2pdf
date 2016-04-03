package sample.database;

import org.javalite.activejdbc.Base;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

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
 * Created by czoeller on 30.03.16.
 */
public class Database {

    private DatabaseConnection databaseConnection;
    final Logger LOG = LoggerFactory.getLogger(Database.class);

    public Database(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
        if( !isInstalled() ) {
            try {
                install();
            } catch (SQLException sqlex) {
                LOG.error("Database installation failed.");
                sqlex.printStackTrace();
            } catch (IOException ex) {
                LOG.error("Database installation failed.");
                ex.printStackTrace();
            }
        }
        openConnection();
    }

    public void install() throws SQLException, IOException {
        Connection connection = DriverManager.getConnection( databaseConnection.PATH );

        LOG.info("Database connection opened successfully");

        Statement statement = connection.createStatement();

        // SQL statement to create tables
        final String sql = new String(Files.readAllBytes(Paths.get(this.getClass().getClassLoader().getResource("sample/database/db.sql").getPath())));

        // execute the statement string
        statement.executeUpdate(sql);

        // cleanup
        statement.close();
        connection.close();
    }

    public boolean isInstalled() {
        boolean installed = false;
        openConnection();
        if( Base.hasConnection() ) {
            installed = 0 < Base.count("sqlite_master", "type = 'table' AND name != 'sqlite_sequence'");
        }
        closeConnection();
        return installed;
    }

    private void openConnection() {
        if( !Base.hasConnection() ) {
            Base.open(databaseConnection.CONNECTOR, databaseConnection.PATH, databaseConnection.USER, databaseConnection.PASSWORD);
        }
    }

    private void closeConnection() {
        Base.close(true);
    }

    public void drop() {
        openConnection();
        final List<Map> all = Base.findAll("SELECT tbl_name FROM sqlite_master WHERE type = 'table' AND tbl_name != 'sqlite_sequence'");
        for ( Map<String, String> map : all ) {
            for ( Map.Entry<String, String> entry : map.entrySet() )
            {
                Base.exec( String.format("DROP table %s", entry.getValue() ) );
            }
        }
        Base.exec("DELETE FROM sqlite_sequence");
    }
}
