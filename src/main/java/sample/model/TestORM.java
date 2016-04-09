package sample.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.database.Database;
import sample.database.DatabaseConnection;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by czoeller on 25.03.16.
 */
public class TestORM {

    private Logger LOG = LoggerFactory.getLogger(TestORM.class);

    public static void main(String[] args) {
        try {
            new TestORM().test();
        } catch (Exception ex) {
            // catch all exceptions to print them before crash
            ex.printStackTrace();
        }

    }

    private void test() throws IOException, SQLException {
        Database database = new Database(DatabaseConnection.getDatabaseConnection());
        database.drop();
        database.install();
        LOG.warn("Database recreated");
    }

}
