package jp.gr.java_conf.hhayakawa_jp.beehive_client;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
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
import jp.gr.java_conf.hhayakawa_jp.beehive_client.model.MeetingCreator;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.model.MeetingParticipantUpdater;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.model.MeetingUpdater;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.model.OccurrenceParticipantStatus;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.model.OccurrenceStatus;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.model.OccurrenceType;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.model.Priority;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.model.RelativeTrigger;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.model.TimedTrigger;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.model.Transparency;
import jp.gr.java_conf.hhayakawa_jp.beehive_client.model.TriggerRelativeTo;

public class InvtCreateInvokerTest {

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
        // BeeId
        List<BeeId> calendar = new ArrayList<BeeId>(1);
        calendar.add(new BeeId(calendar_id));

        // MeetingUpdater
        ZonedDateTime start = ZonedDateTime.of(
                2016, 5, 30, 0, 0, 0, 0, ZoneId.of("Asia/Tokyo"));
        ZonedDateTime end = ZonedDateTime.of(
                2016, 5, 30, 1, 0, 0, 0, ZoneId.of("Asia/Tokyo"));
        boolean includeOnlineConference = false;
        OccurrenceParticipantStatus inviteeParticipantStatus =
                OccurrenceParticipantStatus.NEEDS_ACTION;
        TimedTrigger inviteePrimaryClientReminderTrigger = new RelativeTrigger(
                Duration.ofMinutes(-30).toString(), TriggerRelativeTo.START);
        Priority inviteePriority = Priority.MEDIUM;
        Transparency inviteeTransparency = Transparency.TRANSPARENT;
        String locationName = "JP-OAC-CONF-17006_17M1";
        List<MeetingParticipantUpdater> participantUpdaters = null;
        OccurrenceStatus status = OccurrenceStatus.TENTATIVE;
        String textDescription = "Test String of testDescription.";
        String xhtmlFragmentDescription = null;
        MeetingUpdater meetingUpdater = new MeetingUpdater(
                start.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                end.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                includeOnlineConference,
                inviteeParticipantStatus,
                inviteePrimaryClientReminderTrigger,
                inviteePriority, inviteeTransparency,
                locationName,
                participantUpdaters,
                status,
                textDescription,
                xhtmlFragmentDescription);
        
        // OccurenceType
        OccurrenceType type = OccurrenceType.MEETING;

        MeetingCreator meetingCreater =
                new MeetingCreator(calendar, meetingUpdater, type);
        BeehiveInvoker invoker = context.getInvoker(
                BeehiveApiDefinitions.INVT_CREATE);
        invoker.setPayload(meetingCreater);
        try {
            JsonNode json = invoker.invoke();
            System.out.println(json);
        } catch (IOException | BeeClientException e) {
            System.out.println(e.getCause().getMessage());
            fail(e.getMessage());
        }
    }

}
