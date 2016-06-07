package jp.gr.java_conf.hhayakawa_jp.beehive_client.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeetingCreator {

    private final String beeType = "meetingCreator";
    private final BeeId calendar;
    private final MeetingUpdater meetingUpdater;
    private final OccurrenceType type;

    public MeetingCreator(BeeId calendar, MeetingUpdater meetingUpdater,
            OccurrenceType type) {
        super();
        this.calendar = calendar;
        this.meetingUpdater = meetingUpdater;
        this.type = type;
    }

    public String getBeeType() {
        return beeType;
    }

    public BeeId getCalendar() {
        return calendar;
    }

    public MeetingUpdater getMeetingUpdater() {
        return meetingUpdater;
    }

    public OccurrenceType getType() {
        return type;
    }

}
