package jp.gr.java_conf.hhayakawa_jp.beehive4j.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeetingCreator {

    private final String beeType = "meetingCreator";
    private final BeeId calendar;
    private final MeetingUpdater meetingUpdater;
    private final OccurrenceType type;

    public static class Builder {
        private BeeId calendar;
        private MeetingUpdater meetingUpdater;
        private OccurrenceType type;

        public Builder calendar(BeeId calendar) {
            this.calendar = calendar;
            return this;
        }

        public Builder meetingUpdater(MeetingUpdater meetingUpdater) {
            this.meetingUpdater = meetingUpdater;
            return this;
        }

        public Builder type(OccurrenceType type) {
            this.type = type;
            return this;
        }

        public MeetingCreator build() {
            return new MeetingCreator(
                    this.calendar,
                    this.meetingUpdater,
                    this.type);
        }
    }

    private MeetingCreator(
            BeeId calendar, MeetingUpdater meetingUpdater, OccurrenceType type) {
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
