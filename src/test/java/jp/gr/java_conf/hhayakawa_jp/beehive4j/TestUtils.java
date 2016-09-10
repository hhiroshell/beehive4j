package jp.gr.java_conf.hhayakawa_jp.beehive4j;

import static org.junit.Assert.fail;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;

import jp.gr.java_conf.hhayakawa_jp.beehive4j.exception.BeehiveApiFaultException;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.BeeId;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.ChangeStatus;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.MeetingCreator;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.MeetingParticipantUpdater;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.MeetingParticipantUpdaterOperation;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.MeetingUpdater;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.OccurrenceParticipantStatus;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.OccurrenceStatus;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.OccurrenceType;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.Priority;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.TimedTrigger;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.Transparency;

class TestUtils {

    static final BeehiveContext setUpContext() {
        String host = System.getProperty("beehive4j.test.host");
        String user = System.getProperty("beehive4j.test.user");
        String password = System.getProperty("beehive4j.test.password");
        BeehiveContext context = null;
        try {
            context = BeehiveContext.getBeehiveContext(
                    new URL(host), user, password);
        } catch (MalformedURLException | BeehiveApiFaultException e) {
            fail(e.getMessage());
        }
        if (context == null) {
            fail("failed to setup context.");
        }
        return context;
    }

    static String getDefaultCalendar(BeehiveContext context) {
        MyWorkspaceInvoker invoker = context.getInvoker(
                BeehiveApiDefinitions.TYPEDEF_MY_WORKSPACE);
        ResponseEntity<BeehiveResponse> response = null;
        try {
            response = invoker.invoke();
        } catch (BeehiveApiFaultException e) {
            fail(e.getMessage());
        }
        BeehiveResponse body = response.getBody();
        if (body == null) {
            return null;
        }
        return getNodeAsText(body.getJson(), "defaultCalendar", "collabId", "id");
    }

    static String setUpSingleMeeting(BeehiveContext context, BeeId calendar) {
        // MeetingUpdater
        ZonedDateTime start = ZonedDateTime.now();
        ZonedDateTime end = start.plusHours(1);
        String name = "Test Meeting.";
        ChangeStatus changeStatus = null;
        ZonedDateTime userCreatedOn = null;
        ZonedDateTime userModifiedOn = null;
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
        String invitation_id = null;
        try {
            ResponseEntity<BeehiveResponse> response = invoker.invoke();
            JsonNode json = response.getBody().getJson();
            invitation_id = json.get("collabId").get("id").asText();
        } catch (BeehiveApiFaultException e) {
            System.out.println(e.getCause().getMessage());
            fail(e.getMessage());
        }
        if (invitation_id == null || invitation_id.length() == 0) {
            fail("failed to setup a meeting.");
        }
        return invitation_id;
    }

    private static String getNodeAsText(JsonNode node, String... names) {
        if (node == null) {
            throw new NullPointerException();
        }
        if (names.length == 0) {
            return node.asText();
        }
        for (String name : names) {
            if (!node.has(name)) {
                throw new IllegalArgumentException(
                        "Json data and requird field names aren't consistent.");
            }
            if ((node = node.get(name)) == null) {
                return null;
            }
        }
        return node.asText();
    }

}
