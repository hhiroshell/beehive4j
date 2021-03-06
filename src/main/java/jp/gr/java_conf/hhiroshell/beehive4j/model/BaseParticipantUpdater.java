package jp.gr.java_conf.hhiroshell.beehive4j.model;

public class BaseParticipantUpdater {

    private final String beeType = "baseParticipantUpdater";

    public static class Builder {

        public BaseParticipantUpdater build() {
            return new BaseParticipantUpdater();
        }
    }

    protected BaseParticipantUpdater() {}

    public String getBeeType() {
        return beeType;
    }

}
