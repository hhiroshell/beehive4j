package jp.gr.java_conf.hhayakawa_jp.beehive_client;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import jp.gr.java_conf.hhayakawa_jp.beehive_client.exception.BeeClientException;

public class BeehiveContextTest {

    private String user = null;

    private String password = null;

    @Before
    public void loadTestProperties() {
        user = System.getProperty("beehive4j.test.user");
        password = System.getProperty("beehive4j.test.password");
    }

    @Test
    public void testGetBeehiveContext() {
        try {
            BeehiveContext context =
                    BeehiveContext.getBeehiveContext(user, password);
            assertNotNull(context);
        } catch (BeeClientException e) {
            fail();
        }
    }

}
