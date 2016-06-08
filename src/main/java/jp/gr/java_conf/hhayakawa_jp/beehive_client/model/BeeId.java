package jp.gr.java_conf.hhayakawa_jp.beehive_client.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class BeeId {

    // TODO Implement the type attribute correctly.
    private final String beeType = "beeId";
    private final String id;
    private final String resourceType;

    public BeeId(String id, String resourceType) {
        super();
        this.id = id;
        this.resourceType = resourceType;
    }

    public String getBeeType() {
        return beeType;
    }

    public String getId() {
        return id;
    }

    public String getResourceType() {
        return resourceType;
    }

    @Override
    public String toString() {
        return "BeeIdPayload [beeType=" + beeType + ", id=" + id + "]";
    }

}