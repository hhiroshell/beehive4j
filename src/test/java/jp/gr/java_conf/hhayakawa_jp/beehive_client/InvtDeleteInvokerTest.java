package jp.gr.java_conf.hhayakawa_jp.beehive_client;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;

import jp.gr.java_conf.hhayakawa_jp.beehive_client.exception.BeeClientException;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.model.BeeId;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.model.ChangeStatus;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.model.MeetingCreator;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.model.MeetingParticipantUpdater;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.model.MeetingParticipantUpdaterOperation;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.model.MeetingUpdater;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.model.OccurrenceParticipantStatus;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.model.OccurrenceStatus;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.model.OccurrenceType;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.model.Priority;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.model.TimedTrigger;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.model.Transparency;

public class InvtDeleteInvokerTest {

    private static final String calendar_id =
            "334B:3BF0:clnd:38893C00F42F38A1E0404498C8A6612B000B1A7E0450";

    private BeehiveContext context = null;

    private String invitation_id = null;

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

        // BeeId
        BeeId calendar = new BeeId(calendar_id, null);

        // MeetingUpdater
        ZonedDateTime start = ZonedDateTime.of(
                2016, 6, 5, 13, 0, 0, 0, ZoneId.of("Asia/Tokyo"));
        ZonedDateTime end = ZonedDateTime.of(
                2016, 6, 5, 14, 0, 0, 0, ZoneId.of("Asia/Tokyo"));
        String name = "Test String of name.";
        ChangeStatus changeStatus = null;
        String userCreatedOn = null;
        String userModifiedOn = null;
        boolean includeOnlineConference = false;
        OccurrenceParticipantStatus inviteeParticipantStatus =
                OccurrenceParticipantStatus.ACCEPTED;
        TimedTrigger inviteePrimaryClientReminderTrigger = null;
        Priority inviteePriority = Priority.MEDIUM;
        Transparency inviteeTransparency = Transparency.TRANSPARENT;
        String locationName = "JP-OAC-CONF-17006_17M1";
        List<MeetingParticipantUpdater> participantUpdaters = 
                new ArrayList<MeetingParticipantUpdater>(1);
        participantUpdaters.add(new MeetingParticipantUpdater(
                "mailto:JP-OAC-CONF-17006_17M1@oracle.com", null,
                MeetingParticipantUpdaterOperation.ADD,
                new BeeId("334B:3BF0:bkrs:38893C00F42F38A1E0404498C8A6612B0001DDD86644", null)));
        OccurrenceStatus status = OccurrenceStatus.TENTATIVE;
        String textDescription = "Test String of testDescription.";
        String xhtmlFragmentDescription = null;
        MeetingUpdater meetingUpdater = new MeetingUpdater(
                name, changeStatus, userCreatedOn, userModifiedOn,
                end,
                includeOnlineConference, inviteeParticipantStatus,
                inviteePrimaryClientReminderTrigger, inviteePriority,
                inviteeTransparency, locationName, participantUpdaters,
                start,
                status, textDescription, xhtmlFragmentDescription);
        
        // OccurenceType
        OccurrenceType type = OccurrenceType.MEETING;

        MeetingCreator meetingCreater =
                new MeetingCreator(calendar, meetingUpdater, type);
        InvtCreateInvoker invoker =
                context.getInvoker(BeehiveApiDefinitions.TYPEDEF_INVT_CREATE);
        invoker.setRequestPayload(meetingCreater);
        try {
            JsonNode json = invoker.invoke();
            System.out.println(json);
            invitation_id = json.get("collabId").get("id").asText();
        } catch (IOException | BeeClientException e) {
            System.out.println(e.getCause().getMessage());
            fail(e.getMessage());
        }
    }

    @Test
    public void test() {
        InvtDeleteInvoker invoker =
                context.getInvoker(BeehiveApiDefinitions.TYPEDEF_INVT_DELETE);
        invoker.setPathValue(invitation_id);
        try {
            JsonNode json = invoker.invoke();
            // TODO check response code or meeting does not exist.
            assertNull(json);
        } catch (IOException | BeeClientException e) {
            System.out.println(e.getCause().getMessage());
            fail(e.getMessage());
        }
    }

}

