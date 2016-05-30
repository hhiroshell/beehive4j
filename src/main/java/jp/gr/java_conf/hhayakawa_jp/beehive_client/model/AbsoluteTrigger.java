package jp.gr.java_conf.hhayakawa_jp.beehive_client.model;

public class AbsoluteTrigger extends TimedTrigger {

    private final String value;

    public AbsoluteTrigger(String value) {
        super();
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
