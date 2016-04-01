package sample.model;

import org.javalite.activejdbc.Base;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sample.database.DatabaseConnection;

import java.util.List;

/**
 * Created by czoeller on 28.03.16.
 */
public abstract class ADatabaseSpec extends ADatabaseTest {

    @Test
    public abstract void shouldValidateMandatoryFields();

}
