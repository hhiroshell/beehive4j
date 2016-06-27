package jp.gr.java_conf.hhayakawa_jp.beehive_client;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jp.gr.java_conf.hhayakawa_jp.beehive_client.exception.Beehive4jException;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.exception.BeehiveApiFaultException;

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
        } catch (MalformedURLException | BeehiveApiFaultException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void test() {
        MyWorkspaceInvoker invoker =
                context.getInvoker(BeehiveApiDefinitions.TYPEDEF_MY_WORKSPACE);
        try {
            ResponseEntity<BeehiveResponse> response = invoker.invoke();
            assertEquals("Status code is expected to be 200 (OK).",
                    HttpStatus.OK, response.getStatusCode());
            assertEquals(
                    "BeeType of the resopnse is expected to be \"personalWorkspace\"",
                    "personalWorkspace", response.getBody().getBeeType());
        } catch (Beehive4jException e) {
            fail(e.getMessage());
        }
    }

}
