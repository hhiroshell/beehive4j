package jp.gr.java_conf.hhiroshell.beehive4j.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class BeeIdList {

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