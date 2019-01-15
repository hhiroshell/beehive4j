package jp.gr.java_conf.hhiroshell.beehive4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.ZonedDateTime;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jp.gr.java_conf.hhiroshell.beehive4j.BeehiveApiDefinitions;
import jp.gr.java_conf.hhiroshell.beehive4j.BeehiveContext;
import jp.gr.java_conf.hhiroshell.beehive4j.BeehiveResponse;
import jp.gr.java_conf.hhiroshell.beehive4j.InvtListByRangeInvoker;
import jp.gr.java_conf.hhiroshell.beehive4j.exception.Beehive4jException;
import jp.gr.java_conf.hhiroshell.beehive4j.model.BeeId;
import jp.gr.java_conf.hhiroshell.beehive4j.model.CalendarRange;

public class InvtListByRangeInvokerTest {

    private BeehiveContext context = null;

    @Before
    public void setUp() throws Exception {
        context = TestUtils.setUpContext();
    }

    @Test
    public void test() {
        BeeId defaultCalendarId = new BeeId.Builder()
                .id(TestUtils.getDefaultCalendar(context))
                .build();
        CalendarRange range = new CalendarRange.Builder()
                .beeId(defaultCalendarId)
                .start(ZonedDateTime.now())
                .end(ZonedDateTime.now().plusDays(7))
                .build();
        InvtListByRangeInvoker invoker = context.getInvoker(BeehiveApiDefinitions.TYPEDEF_INVT_LIST_BY_RANGE);
        invoker.setRequestPayload(range);
        try {
            ResponseEntity<BeehiveResponse> response = invoker.invoke();
            // System.out.println(response.getBody().getJson().toString());
            assertEquals("Status code is expected to be 200 (OK).", HttpStatus.OK, response.getStatusCode());
            assertEquals("Beetype is expected to be \"listResult\"","listResult", response.getBody().getBeeType());
        } catch (Beehive4jException e) {
            fail(e.getMessage());
        }
    }

}