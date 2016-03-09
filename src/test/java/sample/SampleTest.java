package sample;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class SampleTest {

    @Test
    public void testSample() {
        int value = 2*2;
        assertThat( "2*2=4", value, is( 4 ) );
    }

}
