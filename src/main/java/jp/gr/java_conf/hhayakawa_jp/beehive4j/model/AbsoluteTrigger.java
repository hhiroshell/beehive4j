package jp.gr.java_conf.hhayakawa_jp.beehive4j.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AbsoluteTrigger extends TimedTrigger {

    private final String beeType = "absoluteTrigger";
    private final String value;

    public AbsoluteTrigger(String value) {
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
