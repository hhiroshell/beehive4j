package jp.gr.java_conf.hhayakawa_jp.beehive4j.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RelativeTrigger extends TimedTrigger {

    private final String beeType = "relativeTrigger";
    private final String offset;
    private final TriggerRelativeTo relativeTo;

    public RelativeTrigger(String offset, TriggerRelativeTo relativeTo) {
        super();
        this.offset = offset;
        this.relativeTo = relativeTo;
    }

    public String getBeeType() {
        return beeType;
    }

    public String getOffset() {
        return offset;
    }

    public TriggerRelativeTo getRelativeTo() {
        return relativeTo;
    }

}
