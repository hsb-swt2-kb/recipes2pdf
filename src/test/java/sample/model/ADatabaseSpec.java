package sample.model;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sample.database.DatabaseConnection;

/**
 * Created by czoeller on 28.03.16.
 */
public abstract class ADatabaseSpec {
    @Before
    public void before() {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Base.open(databaseConnection.CONNECTOR, databaseConnection.PATH, databaseConnection.USER, databaseConnection.PASSWORD);
        Base.openTransaction();
    }

    @After
    public void after() {
        Base.rollbackTransaction();
        Base.close();
    }

    @Test
    public abstract void shouldValidateMandatoryFields();
}
