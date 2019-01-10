package jp.gr.java_conf.hhiroshell.beehive4j;

import com.fasterxml.jackson.databind.JsonNode;

public class BeehiveResponse {

    private final String beeType;

    private final JsonNode json;

    public BeehiveResponse(String beeType, JsonNode json) {
        super();
        this.beeType = beeType;
        this.json = json;
    }

    public String getBeeType() {
        return beeType;
    }

    public JsonNode getJson() {
        return json;
    }

}
