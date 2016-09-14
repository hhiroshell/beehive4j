package jp.gr.java_conf.hhayakawa_jp.beehive4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jp.gr.java_conf.hhayakawa_jp.beehive4j.exception.Beehive4jException;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.BeeId;

public class InvtDeleteInvokerTest {

    private BeehiveContext context = null;

    private String invitation_id = null;

    @Before
    public void setUp() throws Exception {
        context = TestUtils.setUpContext();
        BeeId defaultCalendarId = new BeeId.Builder()
                .id(TestUtils.getDefaultCalendar(context))
                .build();
        invitation_id = TestUtils.setUpSingleMeeting(context, defaultCalendarId);
    }

    @Test
    public void test() {
        InvtDeleteInvoker invoker =
                context.getInvoker(BeehiveApiDefinitions.TYPEDEF_INVT_DELETE);
        invoker.setPathValue(invitation_id);
        try {
            ResponseEntity<BeehiveResponse> response = invoker.invoke();
            assertEquals("Response code is expected to be 204 (No Content)",
                    HttpStatus.NO_CONTENT, response.getStatusCode());
            assertNull("Response body is expected to be null.",
                    response.getBody());
        } catch (Beehive4jException e) {
            System.out.println(e.getMessage());
            fail(e.getMessage());
        }
    }

}
