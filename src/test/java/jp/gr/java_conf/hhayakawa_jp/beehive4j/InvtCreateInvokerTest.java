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

    private static final String PARTICIPANT_RESOURCE_ID =
            "334B:3BF0:bkrs:38893C00F42F38A1E0404498C8A6612B0001DDD86644";
    private static final String PARTICIPANT_RESOURCE_ADDRESS =
            "mailto:JP-OAC-CONF-17006_17M1@oracle.com";

    private BeehiveContext context = null;

    private String invitation_id = null;

    @Before
    public void setUp() throws Exception {
        context = TestUtils.setUpContext();
    }

    @Test
    public void test() {
        // BeeId
        BeeId defaultCalendarId = new BeeId.Builder()
                .id(TestUtils.getDefaultCalendar(context))
                .build();

        // MeetingUpdater
        List<MeetingParticipantUpdater> participantUpdaters = 
                new ArrayList<MeetingParticipantUpdater>(1);
        BeeId participant_id = new BeeId.Builder()
                .id(PARTICIPANT_RESOURCE_ID)
                .build();
        MeetingParticipantUpdater meetingParticipantUpdater = 
                new MeetingParticipantUpdater.Builder()
                    .address(PARTICIPANT_RESOURCE_ADDRESS)
                    .operation(MeetingParticipantUpdaterOperation.ADD)
                    .beeId(participant_id)
                    .build();
        participantUpdaters.add(meetingParticipantUpdater);
        ZonedDateTime start = ZonedDateTime.now().plusDays(1).withHour(9).withMinute(0);
        System.out.println(start);
        MeetingUpdater meetingUpdater = new MeetingUpdater.Builder()
                .start(start)
                .end(start.plusHours(1))
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
                .calendar(defaultCalendarId)
                .meetingUpdater(meetingUpdater)
                .type(type)
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
        TestUtils.tearDownSingleMeeting(context, invitation_id);
    }

}
