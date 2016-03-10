package sample;


import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by czoeller on 09.03.16.
 */
public class ControllerTest {
    @Test
    public void testSample() {
        Controller controller = new Controller();
        assertThat(controller, notNullValue());
    }
}
