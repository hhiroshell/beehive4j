package jp.gr.java_conf.hhayakawa_jp.beehive_client.model;

import jp.gr.java_conf.hhayakawa_jp.beehive_client.BeehiveApiPayload;

public final class BeeId implements BeehiveApiPayload {

    // TODO Implement the type attribute correctly.
    private final String beeType = "beeId";
    private final String id;

    public BeeId(String id) {
        super();
        this.id = id;
    }

    public String getBeeType() {
        return beeType;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "BeeIdPayload [beeType=" + beeType + ", id=" + id + "]";
    }

}