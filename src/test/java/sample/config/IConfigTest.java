package sample.config;

import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by kai on 26.05.16.
 */
public class IConfigTest {
    @Test
    public void testGetInstance(){
        IConfig config = IConfig.getInstance();
        assertNotNull("IConfig.getInstance() must not be null",config);
    }

    @Test
    public void testSetAndGetProperty(){
        IConfig config = IConfig.getInstance();
        config.setProperty("Testdata","getPropertyTest");
        assertEquals("getPropertyTest",config.getProperty("Testdata"));
    }
}
