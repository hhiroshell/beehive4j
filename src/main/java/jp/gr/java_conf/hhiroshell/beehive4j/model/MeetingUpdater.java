package jp.gr.java_conf.hhiroshell.beehive4j.model;

import java.time.ZonedDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeetingUpdater extends ArtifactUpdater {

    private final String beeType = "meetingUpdater";
    private final ZonedDateTime end;
    private final boolean includeOnlineConference;
    private final OccurrenceParticipantStatus inviteeParticipantStatus;
    private final TimedTrigger inviteePrimaryClientReminderTrigger;
    private final Priority inviteePriority;
    // not yet supported.
    //private final CollaboPropertiesUpdater inviteePropertiesUpdater;
    private final Transparency inviteeTransparency;
    private final String locationName;
    private final List<MeetingParticipantUpdater> participantUpdaters;
    private final ZonedDateTime start;
    private final OccurrenceStatus status;
    private final String textDescription;
    private final String xhtmlFragmentDescription;

    public static class Builder {

        private String name;
        private ChangeStatus changeStatus;
        private ZonedDateTime userCreatedOn;
        private ZonedDateTime userModifiedOn;
        private ZonedDateTime end;
        private boolean includeOnlineConference;
        private OccurrenceParticipantStatus inviteeParticipantStatus;
        private TimedTrigger inviteePrimaryClientReminderTrigger;
        private Priority inviteePriority;
        private Transparency inviteeTransparency;
        private String locationName;
        private List<MeetingParticipantUpdater> participantUpdaters;
        private ZonedDateTime start;
        private OccurrenceStatus status;
        private String textDescription;
        private String xhtmlFragmentDescription;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder end(ZonedDateTime end) {
            this.end = end;
            return this;
        }

        public Builder includeOnlineConference(boolean includeOnlineConference) {
            this.includeOnlineConference = includeOnlineConference;
            return this;
        }

        public Builder inviteeParticipantStatus(OccurrenceParticipantStatus inviteeParticipantStatus) {
            this.inviteeParticipantStatus = inviteeParticipantStatus;
            return this;
        }

        public Builder inviteePrimaryClientReminderTrigger(TimedTrigger inviteePrimaryClientReminderTrigger) {
            this.inviteePrimaryClientReminderTrigger = inviteePrimaryClientReminderTrigger;
            return this;
        }

        public Builder inviteePriority(Priority inviteePriority) {
            this.inviteePriority = inviteePriority;
            return this;
        }

        public Builder inviteeTransparency(Transparency inviteeTransparency) {
            this.inviteeTransparency = inviteeTransparency;
            return this;
        }

        public Builder locationName(String locationName) {
            this.locationName = locationName;
            return this;
        }

        public Builder participantUpdaters(List<MeetingParticipantUpdater> participantUpdaters) {
            this.participantUpdaters = participantUpdaters;
            return this;
        }

        public Builder start(ZonedDateTime start) {
            this.start = start;
            return this;
        }

        public Builder status(OccurrenceStatus status) {
            this.status = status;
            return this;
        }

        public Builder textDescription(String textDescription) {
            this.textDescription = textDescription;
            return this;
        }

        public Builder xhtmlFragmentDescription(String xhtmlFragmentDescription) {
            this.xhtmlFragmentDescription = xhtmlFragmentDescription;
            return this;
        }

        public MeetingUpdater build() {
            return new MeetingUpdater(
                    this.name,
                    this.changeStatus,
                    this.userCreatedOn,
                    this.userModifiedOn,
                    this.end,
                    this.includeOnlineConference,
                    this.inviteeParticipantStatus,
                    this.inviteePrimaryClientReminderTrigger,
                    this.inviteePriority,
                    this.inviteeTransparency,
                    this.locationName,
                    this.participantUpdaters,
                    this.start,
                    this.status,
                    this.textDescription,
                    this.xhtmlFragmentDescription);
        }
    }

    private MeetingUpdater(
            String name,
            ChangeStatus changeStatus,
            ZonedDateTime userCreatedOn,
            ZonedDateTime userModifiedOn,
            ZonedDateTime end,
            boolean includeOnlineConference,
            OccurrenceParticipantStatus inviteeParticipantStatus,
            TimedTrigger inviteePrimaryClientReminderTrigger,
            Priority inviteePriority,
            Transparency inviteeTransparency,
            String locationName,
            List<MeetingParticipantUpdater> participantUpdaters,
            ZonedDateTime start,
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

    public ZonedDateTime getEnd() {
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

    public ZonedDateTime getStart() {
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
