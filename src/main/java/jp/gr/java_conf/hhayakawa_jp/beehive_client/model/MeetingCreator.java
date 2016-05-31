package jp.gr.java_conf.hhayakawa_jp.beehive_client.model;

import java.util.List;

import jp.gr.java_conf.hhayakawa_jp.beehive_client.BeehiveApiPayload;

public class MeetingCreator implements BeehiveApiPayload {

    private final List<BeeId> calendar;
    private final MeetingUpdater meetingUpdater;
    private final OccurrenceType type;

    public MeetingCreator(List<BeeId> calendar, MeetingUpdater meetingUpdater,
            OccurrenceType type) {
        super();
        this.calendar = calendar;
        this.meetingUpdater = meetingUpdater;
        this.type = type;
    }

    public List<BeeId> getCalendar() {
        return calendar;
    }

    public MeetingUpdater getMeetingUpdater() {
        return meetingUpdater;
    }

    public OccurrenceType getType() {
        return type;
    }

}
