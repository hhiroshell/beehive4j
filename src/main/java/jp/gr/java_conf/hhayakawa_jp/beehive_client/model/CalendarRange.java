package jp.gr.java_conf.hhayakawa_jp.beehive_client.model;

import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class CalendarRange {

    private final String beeType = "calendarRangeQuery";
    private final BeeId beeId;
    private final ZonedDateTime start;
    private final ZonedDateTime end;

    public CalendarRange(BeeId beeId, ZonedDateTime start, ZonedDateTime end) {
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
