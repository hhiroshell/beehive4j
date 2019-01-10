package jp.gr.java_conf.hhiroshell.beehive4j.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class RelativeTrigger extends TimedTrigger {

    private final String beeType = "relativeTrigger";
    private final String offset;
    private final TriggerRelativeTo relativeTo;

    public static class Builder {

        private String offset;
        private TriggerRelativeTo relativeTo;

        public Builder offset(String offset) {
            this.offset = offset;
            return this;
        }

        public Builder relativeTo(TriggerRelativeTo relativeTo) {
            this.relativeTo = relativeTo;
            return this;
        }

        public RelativeTrigger build() {
            return new RelativeTrigger(offset, relativeTo);
        }

    }

    private RelativeTrigger(String offset, TriggerRelativeTo relativeTo) {
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
