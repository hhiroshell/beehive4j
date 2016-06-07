package jp.gr.java_conf.hhayakawa_jp.beehive_client.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeetingUpdater extends ArtifactUpdater {

    private final String beeType = "meetingUpdater";
    private final String end;
    private final boolean includeOnlineConference;
    private final OccurrenceParticipantStatus inviteeParticipantStatus;
    private final TimedTrigger inviteePrimaryClientReminderTrigger;
    private final Priority inviteePriority;
    // not yet supported.
    //private final CollaboPropertiesUpdater inviteePropertiesUpdater;
    private final Transparency inviteeTransparency;
    private final String locationName;
    private final List<MeetingParticipantUpdater> participantUpdaters;
    private final String start;
    private final OccurrenceStatus status;
    private final String textDescription;
    private final String xhtmlFragmentDescription;


    public MeetingUpdater(
            String name,
            ChangeStatus changeStatus,
            String userCreatedOn,
            String userModifiedOn,
            String end,
            boolean includeOnlineConference,
            OccurrenceParticipantStatus inviteeParticipantStatus,
            TimedTrigger inviteePrimaryClientReminderTrigger,
            Priority inviteePriority,
            Transparency inviteeTransparency,
            String locationName,
            List<MeetingParticipantUpdater> participantUpdaters,
            String start,
            OccurrenceStatus status,
            String textDescription,
            String xhtmlFragmentDescription) {
        super(name, changeStatus, userCreatedOn, userModifiedOn);
        this.end = end;
        this.includeOnlineConference = includeOnlineConference;
        this.inviteeParticipantStatus = inviteeParticipantStatus;
        this.inviteePrimaryClientReminderTrigger = inviteePrimaryClientReminderTrigger;
        this.inviteePriority = inviteePriority;
        this.inviteeTransparency = inviteeTransparency;
        this.locationName = locationName;
        this.participantUpdaters = participantUpdaters;
        this.start = start;
        this.status = status;
        this.textDescription = textDescription;
        this.xhtmlFragmentDescription = xhtmlFragmentDescription;
    }

    public String getBeeType() {
        return beeType;
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

    public Transparency getInviteeTransparency() {
        return inviteeTransparency;
    }

    public String getLocationName() {
        return locationName;
    }

    public List<MeetingParticipantUpdater> getParticipantUpdaters() {
        return participantUpdaters;
    }

    public String getStart() {
        return start;
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
