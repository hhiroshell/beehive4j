package jp.gr.java_conf.hhiroshell.beehive4j.model;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class CalendarRange {

    private final String beeType = "calendarRangeQuery";
    private final BeeId beeId;
    private final ZonedDateTime start;
    private final ZonedDateTime end;

    public static class Builder {
        private BeeId beeId;
        private ZonedDateTime start;
        private ZonedDateTime end;

        public Builder beeId(BeeId beeId) {
            this.beeId = beeId;
            return this;
        }

        public Builder start(ZonedDateTime start) {
            this.start = start;
            return this;
        }

        public Builder end(ZonedDateTime end) {
            this.end = end;
            return this;
        }

        public CalendarRange build() {
            return new CalendarRange(beeId, start, end);
        }

    }

    private CalendarRange(BeeId beeId, ZonedDateTime start, ZonedDateTime end) {
        super();
        this.beeId = beeId;
        this.start = start;
        this.end = end;
    }

    public String getBeeType() {
        return beeType;
    }

    public BeeId getCalendar() {
        return beeId;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

}
