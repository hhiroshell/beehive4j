package jp.gr.java_conf.hhayakawa_jp.beehive_client.model;

import jp.gr.java_conf.hhayakawa_jp.beehive_client.BeehiveApiPayload;

public class BaseParticipantUpdater implements BeehiveApiPayload {

    private final String beeType = "baseParticipantUpdater";

    public String getBeeType() {
        return beeType;
    }

}
