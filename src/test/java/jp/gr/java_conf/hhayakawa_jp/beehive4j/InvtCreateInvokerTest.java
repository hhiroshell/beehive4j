package jp.gr.java_conf.hhayakawa_jp.beehive4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;

import jp.gr.java_conf.hhayakawa_jp.beehive4j.exception.Beehive4jException;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.BeeId;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.MeetingCreator;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.MeetingParticipantUpdater;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.MeetingParticipantUpdaterOperation;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.MeetingUpdater;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.OccurrenceParticipantStatus;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.OccurrenceStatus;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.OccurrenceType;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.Priority;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.Transparency;

public class InvtCreateInvokerTest {

    private static final String calendar_id =
            "334B:3BF0:clnd:38893C00F42F38A1E0404498C8A6612B000B1A7E0450";

    private BeehiveContext context = null;

    private String invitation_id = null;

    @Before
    public void setUp() throws Exception {
        context = TestUtils.setUpContext();
    }

    @Test
    public void test() {
        // BeeId
        BeeId calendar = new BeeId(calendar_id, null);

        // MeetingUpdater
        List<MeetingParticipantUpdater> participantUpdaters = 
                new ArrayList<MeetingParticipantUpdater>(1);
        participantUpdaters.add(new MeetingParticipantUpdater(
                "mailto:JP-OAC-CONF-17006_17M1@oracle.com", null,
                MeetingParticipantUpdaterOperation.ADD,
                new BeeId("334B:3BF0:bkrs:38893C00F42F38A1E0404498C8A6612B0001DDD86644", null)));
        MeetingUpdater meetingUpdater = new MeetingUpdater.Builder()
                .start(ZonedDateTime.now())
                .end(ZonedDateTime.now().plusHours(1))
                .name("Test Meeting")
                .includeOnlineConference(false)
                .inviteeParticipantStatus(OccurrenceParticipantStatus.ACCEPTED)
                .inviteePriority(Priority.MEDIUM)
                .inviteeTransparency(Transparency.TRANSPARENT)
                .locationName("JP-OAC-CONF-17006_17M1")
                .participantUpdaters(participantUpdaters)
                .status(OccurrenceStatus.TENTATIVE)
                .textDescription("Description of test meeting.")
                .build();
        
        // OccurenceType
        OccurrenceType type = OccurrenceType.MEETING;

        MeetingCreator meetingCreater = new MeetingCreator.Builder()
                .beeId(calendar)
                .meetingUpdater(meetingUpdater)
                .occurrenceType(type)
                .build();
        InvtCreateInvoker invoker =
                context.getInvoker(BeehiveApiDefinitions.TYPEDEF_INVT_CREATE);
        invoker.setRequestPayload(meetingCreater);
        try {
            ResponseEntity<BeehiveResponse> response = invoker.invoke();
            assertEquals("Response code is expected to be 201 (Created)",
                    HttpStatus.CREATED, response.getStatusCode());
            assertEquals("BeeType is expected to be \"meeting\"",
                    "meeting", response.getBody().getBeeType());
            JsonNode json = response.getBody().getJson();
            invitation_id = json.get("collabId").get("id").asText();
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
