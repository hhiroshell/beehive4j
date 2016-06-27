package jp.gr.java_conf.hhayakawa_jp.beehive_client;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import jp.gr.java_conf.hhayakawa_jp.beehive_client.exception.BeehiveApiFaultException;

public class BeehiveContextTest {

    private String host = null;

    private String user = null;

    private String password = null;

    @Before
    public void loadTestProperties() {
        host = System.getProperty("beehive4j.test.host");
        user = System.getProperty("beehive4j.test.user");
        password = System.getProperty("beehive4j.test.password");
    }

    @Test
    public void testGetBeehiveContext() {
        try {
            BeehiveContext context = BeehiveContext.getBeehiveContext(
                    new URL(this.host), user, password);
            assertNotNull(context);
        } catch (MalformedURLException | BeehiveApiFaultException e) {
            fail();
        }
    }

}
