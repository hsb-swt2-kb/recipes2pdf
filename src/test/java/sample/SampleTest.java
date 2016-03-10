package sample;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SampleTest {

    @Test
    public void testSample() {
        int value = 2 * 2;
        assertThat("2*2=4", value, is(4));
    }

}
