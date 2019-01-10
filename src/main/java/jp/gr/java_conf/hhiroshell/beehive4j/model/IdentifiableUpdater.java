package jp.gr.java_conf.hhiroshell.beehive4j.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class IdentifiableUpdater {

    private final String beeType = "identifiableUpdater";

    public static class Builder {

        public IdentifiableUpdater build() {
            return new IdentifiableUpdater();
        }

    }

    protected IdentifiableUpdater() {}

    public String getBeeType() {
        return beeType;
    }

}
