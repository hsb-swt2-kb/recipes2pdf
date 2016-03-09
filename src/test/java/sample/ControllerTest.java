package sample;


import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

/**
 * Created by czoeller on 09.03.16.
 */
public class ControllerTest {
    @Test
    public void testSample() {
        Controller controller = new Controller();
        assertThat( controller, notNullValue() );
    }
}
