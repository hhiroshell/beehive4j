package jp.gr.java_conf.hhayakawa_jp.beehive4j.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IdentifiableUpdater {

    private final String beeType = "identifiableUpdater";

    public String getBeeType() {
        return beeType;
    }

}