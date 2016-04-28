package sample.model.activejdbc;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import sample.database.Database;
import sample.database.DatabaseConnection;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by czoeller on 30.03.16.
 */
public class ADatabaseTest {

    @Before
    public void before() throws IOException, SQLException {
        Database database = new Database( DatabaseConnection.getSandboxDatabaseConnection() );
        database.drop();
        database.install();
        Base.openTransaction();
    }

    @After
    public void after() {
        Base.rollbackTransaction();
        Base.close();
    }
}
