package jp.gr.java_conf.hhayakawa_jp.beehive_client;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import jp.gr.java_conf.hhayakawa_jp.beehive_client.exception.BeeClientException;

public class MyWorkspaceInvokerTest {

    private BeehiveContext context = null;

    @Before
    public void setUp() throws Exception {
        String host = System.getProperty("beehive4j.test.host");
        String user = System.getProperty("beehive4j.test.user");
        String password = System.getProperty("beehive4j.test.password");
        try {
            context = BeehiveContext.getBeehiveContext(
                    new URL(host), user, password);
        } catch (MalformedURLException | BeeClientException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void test() {
        BeehiveInvoker invoker = 
                context.getInvoker(BeehiveApiDefinitions.MY_WORKSPACE);
        try {
            JsonNode json = invoker.invoke();
            assertNotNull(json);
            assertEquals("personalWorkspace", json.get("beeType").asText());
        } catch (IOException | BeeClientException e) {
            fail(e.getMessage());
        }
    }

}
