package jp.gr.java_conf.hhayakawa_jp.beehive_client.model;

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
    // not yet support.
//    private final List<MeetingParticipantUpdater> participantUpdaters;
    private final OccurrenceStatus status;
    private final String textDescription;
    private final String xhtmlFragmentDescription;

    public MeetingUpdater(String start, String end, boolean includeOnlineConference,
            OccurrenceParticipantStatus inviteeParticipantStatus, TimedTrigger inviteePrimaryClientReminderTrigger,
            Priority inviteePriority, Transparency inviteeTransparency, String locationName, OccurrenceStatus status,
            String textDescription, String xhtmlFragmentDescription) {
        super();
        this.start = start;
        this.end = end;
        this.includeOnlineConference = includeOnlineConference;
        this.inviteeParticipantStatus = inviteeParticipantStatus;
        this.inviteePrimaryClientReminderTrigger = inviteePrimaryClientReminderTrigger;
        this.inviteePriority = inviteePriority;
        this.inviteeTransparency = inviteeTransparency;
        this.locationName = locationName;
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

//    public CollabPropertiesUpdater getInviteePropertiesUpdater() {
//        return inviteePropertiesUpdater;
//    }

    public Transparency getInviteeTransparency() {
        return inviteeTransparency;
    }

    public String getLocationName() {
        return locationName;
    }

//    public List<ParticipantUpdater> getParticipantUpdaters() {
//        return participantUpdaters;
//    }

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
