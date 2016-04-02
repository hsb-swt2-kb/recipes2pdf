package sample.model;

import org.junit.Test;

/**
 * Created by czoeller on 28.03.16.
 */
public abstract class ADatabaseSpec extends ADatabaseTest {

    @Test
    public abstract void shouldValidateMandatoryFields();

}
