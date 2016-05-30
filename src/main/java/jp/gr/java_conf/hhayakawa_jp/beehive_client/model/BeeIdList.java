package jp.gr.java_conf.hhayakawa_jp.beehive_client.model;

import java.util.List;

import jp.gr.java_conf.hhayakawa_jp.beehive_client.BeehiveApiPayload;

public final class BeeIdList implements BeehiveApiPayload {

    private final String beeType = "beeIdList";
    private final List<BeeId> beeId;
    
    public BeeIdList(List<BeeId> beeId) {
        super();
        this.beeId = beeId;
    }

    public String getBeeType() {
        return beeType;
    }

    public List<BeeId> getBeeId() {
        return beeId;
    }

    @Override
    public String toString() {
        return "BeeIdListPayload [beeType=" + beeType + ", beeId=" + beeId + "]";
    }

}