package jp.gr.java_conf.hhayakawa_jp.beehive_client.model;

import java.time.ZonedDateTime;

public abstract class ArtifactUpdater extends EntityUpdater {

    protected final ChangeStatus changeStatus;
    // not yet supported.
    //protected final CollabPropertiesUpdater propertiesUpdater;
    protected final ZonedDateTime userCreatedOn;
    protected final ZonedDateTime userModifiedOn;
    // not yet supported.
    //protected final CollabPropertiesUpdater viewerPropertiesUpdater;

    public ArtifactUpdater(String name, ChangeStatus changeStatus,
            ZonedDateTime userCreatedOn, ZonedDateTime userModifiedOn) {
        super(name);
        this.changeStatus = changeStatus;
        this.userCreatedOn = userCreatedOn;
        this.userModifiedOn = userModifiedOn;
    }

    public ChangeStatus getChangeStatus() {
        return changeStatus;
    }

    public ZonedDateTime getUserCreatedOn() {
        return userCreatedOn;
    }

    public ZonedDateTime getUserModifiedOn() {
        return userModifiedOn;
    }

}
