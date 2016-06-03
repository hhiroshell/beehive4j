package jp.gr.java_conf.hhayakawa_jp.beehive_client;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import jp.gr.java_conf.hhayakawa_jp.beehive_client.exception.BeeClientException;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.model.BeeId;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.model.BeeIdList;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.model.CalendarRange;

public class InvtReadBatchInvokerTest {

    private static final String calendar_id =
            "334B:3BF0:clnd:38893C00F42F38A1E0404498C8A6612B0001DDFA21CC";

    private BeehiveContext context = null;

    private List<String> invitation_ids = null;

    @Before
    public void setUp() throws Exception {
        // login
        String host = System.getProperty("beehive4j.test.host");
        String user = System.getProperty("beehive4j.test.user");
        String password = System.getProperty("beehive4j.test.password");
        try {
            context = BeehiveContext.getBeehiveContext(
                    new URL(host), user, password);
        } catch (MalformedURLException | BeeClientException e) {
            fail(e.getMessage());
        }

        // get list of invitation ids.
        ZonedDateTime from = ZonedDateTime.now();
        ZonedDateTime to = ZonedDateTime.now().plusDays(7);
        CalendarRange range = new CalendarRange(
                new BeeId(calendar_id),
                from.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                to.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
        BeehiveInvoker invoker =
                context.getInvoker(BeehiveApiDefinitions.INVT_LIST_BYRANGE);
        invoker.setPayload(range);
        try {
            JsonNode json = invoker.invoke();
            invitation_ids = parseInvitaionIds(json);
        } catch (IOException | BeeClientException e) {
            System.out.println(e.getCause().getMessage());
            fail(e.getMessage());
        }
    }

    @Test
    public void test() {
        List<BeeId> beeIds = new ArrayList<BeeId>();
        for (String id : invitation_ids) {
            beeIds.add(new BeeId(id));
        }
        BeeIdList beeIdList = new BeeIdList(beeIds);
        BeehiveInvoker invoker =
                context.getInvoker(BeehiveApiDefinitions.INVT_READ_BATCH);
        invoker.setPayload(beeIdList);
        try {
            JsonNode json = invoker.invoke();
            assertNotNull(json);
        } catch (IOException | BeeClientException e) {
            // TODO cause may be null.
            System.out.println(e.getCause().getMessage());
            fail(e.getMessage());
        }

    }

    private List<String> parseInvitaionIds(JsonNode node) {
        Iterable<JsonNode> elements = node.get("elements");
        if (elements == null) {
            return null;
        }
        List<String> retval = new ArrayList<String>();
        for (JsonNode element : elements) {
            retval.add(element.get("collabId").get("id").asText());
        }
        return retval;
    }

}
