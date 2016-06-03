package jp.gr.java_conf.hhayakawa_jp.beehive_client.model;

import jp.gr.java_conf.hhayakawa_jp.beehive_client.BeehiveApiPayload;

public abstract class ArtifactUpdater
    extends EntityUpdater implements BeehiveApiPayload {

    protected final ChangeStatus changeStatus;
    // not yet supported.
    //protected final CollabPropertiesUpdater propertiesUpdater;
    // TODO: use ZonedDateTime
    protected final String userCreatedOn;
    protected final String userModifiedOn;
    // not yet supported.
    //protected final CollabPropertiesUpdater viewerPropertiesUpdater;

    public ArtifactUpdater(String name, ChangeStatus changeStatus,
            String userCreatedOn, String userModifiedOn) {
        super(name);
        this.changeStatus = changeStatus;
        this.userCreatedOn = userCreatedOn;
        this.userModifiedOn = userModifiedOn;
    }

    public ChangeStatus getChangeStatus() {
        return changeStatus;
    }

    public String getUserCreatedOn() {
        return userCreatedOn;
    }

    public String getUserModifiedOn() {
        return userModifiedOn;
    }

}
