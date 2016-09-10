package jp.gr.java_conf.hhayakawa_jp.beehive4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.ZonedDateTime;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jp.gr.java_conf.hhayakawa_jp.beehive4j.BeehiveApiDefinitions;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.BeehiveContext;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.BeehiveResponse;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.InvtListByRangeInvoker;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.exception.Beehive4jException;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.BeeId;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.CalendarRange;

public class InvtListByRangeInvokerTest {

    private static final String calendar_id =
            "334B:3BF0:clnd:38893C00F42F38A1E0404498C8A6612B0001DDFA21CC";

    private BeehiveContext context = null;

    @Before
    public void setUp() throws Exception {
        context = TestUtils.setUpContext();
    }

    @Test
    public void test() {
        ZonedDateTime from = ZonedDateTime.now();
        ZonedDateTime to = ZonedDateTime.now().plusDays(7);
        CalendarRange range = new CalendarRange(
                new BeeId(calendar_id, null), from, to);
        InvtListByRangeInvoker invoker = context.getInvoker(
                BeehiveApiDefinitions.TYPEDEF_INVT_LIST_BY_RANGE);
        invoker.setRequestPayload(range);
        try {
            ResponseEntity<BeehiveResponse> response = invoker.invoke();
            System.out.println(response.getBody().getJson().toString());
            assertEquals("Status code is expected to be 200 (OK).",
                    HttpStatus.OK, response.getStatusCode());
            assertEquals("Beetype is expected to be \"listResult\"",
                    "listResult", response.getBody().getBeeType());
        } catch (Beehive4jException e) {
            System.out.println(e.getCause().getMessage());
            fail(e.getMessage());
        }
    }

}
