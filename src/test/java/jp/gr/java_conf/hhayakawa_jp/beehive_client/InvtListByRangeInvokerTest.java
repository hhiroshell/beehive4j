package jp.gr.java_conf.hhayakawa_jp.beehive_client;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jp.gr.java_conf.hhayakawa_jp.beehive_client.exception.BeeClientException;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.model.BeeId;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.model.CalendarRange;

public class InvtListByRangeInvokerTest {

    private static final String calendar_id =
            "334B:3BF0:clnd:38893C00F42F38A1E0404498C8A6612B0001DDFA21CC";

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
        ZonedDateTime from = ZonedDateTime.now();
        ZonedDateTime to = ZonedDateTime.now().plusDays(7);
        CalendarRange range = new CalendarRange(
                new BeeId(calendar_id, null),
                from.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                to.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        InvtListByRangeInvoker invoker = context.getInvoker(
                BeehiveApiDefinitions.TYPEDEF_INVT_LIST_BY_RANGE);
        invoker.setRequestPayload(range);
        try {
            ResponseEntity<BeehiveResponse> response = invoker.invoke();
            assertEquals("Status code is expected to be 200 (OK).",
                    HttpStatus.OK, response.getStatusCode());
            assertEquals("Beetype is expected to be \"listResult\"",
                    "listResult", response.getBody().getBeeType());
        } catch (IOException | BeeClientException e) {
            System.out.println(e.getCause().getMessage());
            fail(e.getMessage());
        }
    }

}
