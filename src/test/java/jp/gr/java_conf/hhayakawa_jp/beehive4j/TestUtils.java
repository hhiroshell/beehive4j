package jp.gr.java_conf.hhayakawa_jp.beehive4j;

import static org.junit.Assert.fail;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZonedDateTime;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;

import jp.gr.java_conf.hhayakawa_jp.beehive4j.exception.BeehiveApiFaultException;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.BeeId;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.MeetingCreator;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.MeetingUpdater;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.OccurrenceParticipantStatus;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.OccurrenceStatus;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.OccurrenceType;
import jp.gr.java_conf.hhayakawa_jp.beehive4j.model.Priority;
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
        ZonedDateTime start = ZonedDateTime.now().plusDays(1).withHour(9).withMinute(0);
        MeetingUpdater meetingUpdater = new MeetingUpdater.Builder()
                .start(start)
                .end(start.plusHours(1))
                .name("Test Meeting")
                .includeOnlineConference(false)
                .inviteeParticipantStatus(OccurrenceParticipantStatus.ACCEPTED)
                .inviteePriority(Priority.MEDIUM)
                .inviteeTransparency(Transparency.TRANSPARENT)
                .locationName("Test Meeting Room")
                .status(OccurrenceStatus.TENTATIVE)
                .textDescription("Description of test meeting.")
                .build();

        // OccurenceType
        OccurrenceType type = OccurrenceType.MEETING;

        MeetingCreator meetingCreater = new MeetingCreator.Builder()
                .calendar(calendar)
                .meetingUpdater(meetingUpdater)
                .type(type)
                .build();
        InvtCreateInvoker invoker =
                context.getInvoker(BeehiveApiDefinitions.TYPEDEF_INVT_CREATE);
        invoker.setRequestPayload(meetingCreater);
        String invitation_id = null;
        try {
            ResponseEntity<BeehiveResponse> response = invoker.invoke();
            JsonNode json = response.getBody().getJson();
            invitation_id = json.get("collabId").get("id").asText();
        } catch (BeehiveApiFaultException e) {
            fail(e.getMessage());
        }
        if (invitation_id == null || invitation_id.length() == 0) {
            fail("failed to setup a meeting.");
        }
        return invitation_id;
    }

    static void tearDownSingleMeeting(
            BeehiveContext context, String invitation_id) {
        InvtDeleteInvoker invoker =
                context.getInvoker(BeehiveApiDefinitions.TYPEDEF_INVT_DELETE);
        invoker.setPathValue(invitation_id);
        try {
            invoker.invoke();
        } catch (BeehiveApiFaultException e) {
            fail(e.getMessage());
        }
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
