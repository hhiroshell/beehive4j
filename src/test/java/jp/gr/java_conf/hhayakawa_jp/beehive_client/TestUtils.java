package jp.gr.java_conf.hhayakawa_jp.beehive_client;

import static org.junit.Assert.fail;

import java.net.MalformedURLException;
import java.net.URL;

import jp.gr.java_conf.hhayakawa_jp.beehive_client.exception.BeehiveApiFaultException;

class TestUtils {

    static final BeehiveContext setUpContext() {
        String host = System.getProperty("beehive4j.test.host");
        String user = System.getProperty("beehive4j.test.user");
        String password = System.getProperty("beehive4j.test.password");
        BeehiveContext context = null;
        try {
            context = BeehiveContext.getBeehiveContext(
                    new URL(host), user, password);
        } catch (MalformedURLException | BeehiveApiFaultException e) {
            fail(e.getMessage());
        }
        if (context == null) {
            fail("failed to setup context.");
        }
        return context;
    }

}
