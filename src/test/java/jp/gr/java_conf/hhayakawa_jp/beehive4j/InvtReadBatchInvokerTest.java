package jp.gr.java_conf.hhayakawa_jp.beehive4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;

import jp.gr.java_conf.hhayakawa_jp.beehive4j.BeehiveApiDefinitions;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.BeehiveContext;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.BeehiveResponse;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.InvtListByRangeInvoker;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.InvtReadBatchInvoker;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.exception.Beehive4jException;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.exception.BeehiveApiFaultException;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.BeeId;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.BeeIdList;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.CalendarRange;

public class InvtReadBatchInvokerTest {

    private static final String calendar_id =
            "334B:3BF0:clnd:38893C00F42F38A1E0404498C8A6612B0001DDFA21CC";

    private BeehiveContext context = null;

    private List<String> invitation_ids = null;

    @Before
    public void setUp() throws Exception {
        // login
        context = TestUtils.setUpContext();

        // get list of invitation ids.
        ZonedDateTime from = ZonedDateTime.now();
        ZonedDateTime to = ZonedDateTime.now().plusDays(4);
        CalendarRange range = new CalendarRange(
                new BeeId(calendar_id ,null), from, to);
        InvtListByRangeInvoker invoker = context.getInvoker(
                BeehiveApiDefinitions.TYPEDEF_INVT_LIST_BY_RANGE);
        invoker.setRequestPayload(range);
        try {
            ResponseEntity<BeehiveResponse> response = invoker.invoke();
            // fix null pointer exception.
            invitation_ids = parseInvitaionIds(response.getBody().getJson());
        } catch (BeehiveApiFaultException e) {
            System.out.println(e.getCause().getMessage());
            fail(e.getMessage());
        }
    }

    @Test
    public void test() {
        List<BeeId> beeIds = new ArrayList<BeeId>();
        for (String id : invitation_ids) {
            beeIds.add(new BeeId(id, null));
        }
        BeeIdList beeIdList = new BeeIdList(beeIds);
        InvtReadBatchInvoker invoker = context.getInvoker(
                BeehiveApiDefinitions.TYPEDEF_INVT_READ_BATCH);
        invoker.setRequestPayload(beeIdList);
        try {
            ResponseEntity<BeehiveResponse> response = invoker.invoke();
            System.out.println(response.getBody().getJson().toString());
            assertEquals("Status code is expected to be 200 (OK).",
                    HttpStatus.OK, response.getStatusCode());
            assertEquals("Beetype is expected to be \"list\"", "list",
                    response.getBody().getBeeType());
        } catch (Beehive4jException e) {
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
