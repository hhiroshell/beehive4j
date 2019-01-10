package jp.gr.java_conf.hhiroshell.beehive4j.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class AbsoluteTrigger extends TimedTrigger {

    private final String beeType = "absoluteTrigger";
    private final String value;

    public static class Builder {
        private String value;

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public AbsoluteTrigger build() {
            return new AbsoluteTrigger(value);
        }
    }

    private AbsoluteTrigger(String value) {
        super();
        this.value = value;
    }

    public String getBeeType() {
        return beeType;
    }

    public String getValue() {
        return value;
    }

}
