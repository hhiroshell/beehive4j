package jp.gr.java_conf.hhayakawa_jp.beehive_client.model;

import jp.gr.java_conf.hhayakawa_jp.beehive_client.BeehiveApiPayload;

public abstract class EntityUpdater
    extends IdentifiableUpdater implements BeehiveApiPayload {

    protected final String name;

    public EntityUpdater(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
