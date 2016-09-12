package jp.gr.java_conf.hhayakawa_jp.beehive4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jp.gr.java_conf.hhayakawa_jp.beehive4j.exception.Beehive4jException;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.BeeId;

public class InvtReadInvokerTest {

    private static final String calendar_id =
            "334B:3BF0:clnd:38893C00F42F38A1E0404498C8A6612B000B1A7E0450";

    private BeehiveContext context = null;

    private String invitation_id = null;

    @Before
    public void setUp() throws Exception {
        context = TestUtils.setUpContext();
        BeeId calendar = new BeeId(calendar_id, null);
        invitation_id =  TestUtils.setUpSingleMeeting(context, calendar);
    }

    @Test
    public void test() {
        InvtReadInvoker invoker =
                context.getInvoker(BeehiveApiDefinitions.TYPEDEF_INVT_READ);
        invoker.setPathValue(invitation_id);
        try {
            ResponseEntity<BeehiveResponse> response = invoker.invoke();
            assertEquals("Response code is expected to be 200 (OK)",
                    HttpStatus.OK, response.getStatusCode());
            assertEquals("BeeType is expected to be \"meeting\"",
                    "meeting", response.getBody().getBeeType());
        } catch (Beehive4jException e) {
            System.out.println(e.getMessage());
            fail(e.getMessage());
        }
    }

    @After
    public void tearDown() throws Exception {
        InvtDeleteInvoker invoker =
                context.getInvoker(BeehiveApiDefinitions.TYPEDEF_INVT_DELETE);
        invoker.setPathValue(invitation_id);
        try {
            invoker.invoke();
        } catch (Beehive4jException e) {
            System.out.println(e.getMessage());
            fail(e.getMessage());
        }
    }

}

