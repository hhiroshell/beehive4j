package jp.gr.java_conf.hhayakawa_jp.beehive_client.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import jp.gr.java_conf.hhayakawa_jp.beehive_client.BeehiveApiPayload;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IdentifiableUpdater implements BeehiveApiPayload {

    private String beeType = "identifiableUpdater";

    public String getBeeType() {
        return beeType;
    }

}
