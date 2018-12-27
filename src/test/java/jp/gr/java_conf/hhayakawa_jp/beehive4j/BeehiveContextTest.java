package jp.gr.java_conf.hhayakawa_jp.beehive4j;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import jp.gr.java_conf.hhayakawa_jp.beehive4j.BeehiveContext;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.exception.Beehive4jException;

public class BeehiveContextTest {

    private String host = null;

    private String user = null;

    private String password = null;

    @Before
    public void loadTestProperties() {
        host = System.getProperty("BEEHIVE4J_TEST_HOST");
        user = System.getProperty("BEEHIVE4J_TEST_USER");
        password = System.getProperty("BEEHIVE4J_TEST_PASSWORD");
    }

    @Test
    public void testGetBeehiveContext() {
        try {
            BeehiveContext context = BeehiveContext.getBeehiveContext(
                    new URL(this.host), user, password);
            assertNotNull(context);
        } catch (MalformedURLException | Beehive4jException e) {
            fail();
        }
    }

}
