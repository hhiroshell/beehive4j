package jp.gr.java_conf.hhayakawa_jp.beehive_client.model;

import java.util.List;

import jp.gr.java_conf.hhayakawa_jp.beehive_client.BeehiveApiPayload;

public class MeetingUpdater implements BeehiveApiPayload {

    private final String start;
    private final String end;
    private final boolean includeOnlineConference;
    private final OccurrenceParticipantStatus inviteeParticipantStatus;
    private final TimedTrigger inviteePrimaryClientReminderTrigger;
    private final Priority inviteePriority;
    // not yet support.
//    private final CollabPropertiesUpdater inviteePropertiesUpdater;
    private final Transparency inviteeTransparency;
    private final String locationName;
    private final List<MeetingParticipantUpdater> participantUpdaters;
    private final OccurrenceStatus status;
    private final String textDescription;
    private final String xhtmlFragmentDescription;

    public MeetingUpdater(
            String start, String end,
            boolean includeOnlineConference,
            OccurrenceParticipantStatus inviteeParticipantStatus,
            TimedTrigger inviteePrimaryClientReminderTrigger,
            Priority inviteePriority,
            Transparency inviteeTransparency,
            String locationName,
            List<MeetingParticipantUpdater> participantUpdaters,
            OccurrenceStatus status,
            String textDescription,
            String xhtmlFragmentDescription) {
        super();
        this.start = start;
        this.end = end;
        this.includeOnlineConference = includeOnlineConference;
        this.inviteeParticipantStatus = inviteeParticipantStatus;
        this.inviteePrimaryClientReminderTrigger = inviteePrimaryClientReminderTrigger;
        this.inviteePriority = inviteePriority;
        this.inviteeTransparency = inviteeTransparency;
        this.locationName = locationName;
        this.participantUpdaters = participantUpdaters;
        this.status = status;
        this.textDescription = textDescription;
        this.xhtmlFragmentDescription = xhtmlFragmentDescription;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public boolean isIncludeOnlineConference() {
        return includeOnlineConference;
    }

    public OccurrenceParticipantStatus getInviteeParticipantStatus() {
        return inviteeParticipantStatus;
    }

    public TimedTrigger getInviteePrimaryClientReminderTrigger() {
        return inviteePrimaryClientReminderTrigger;
    }

    public Priority getInviteePriority() {
        return inviteePriority;
    }

    // not yet support.
//    public CollabPropertiesUpdater getInviteePropertiesUpdater() {
//        return inviteePropertiesUpdater;
//    }

    public Transparency getInviteeTransparency() {
        return inviteeTransparency;
    }

    public String getLocationName() {
        return locationName;
    }

    public List<MeetingParticipantUpdater> getParticipantUpdaters() {
        return participantUpdaters;
    }

    public OccurrenceStatus getStatus() {
        return status;
    }

    public String getTextDescription() {
        return textDescription;
    }

    public String getXhtmlFragmentDescription() {
        return xhtmlFragmentDescription;
    }

}
