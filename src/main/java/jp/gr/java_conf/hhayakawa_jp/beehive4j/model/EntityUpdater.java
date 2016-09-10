package jp.gr.java_conf.hhayakawa_jp.beehive4j.model;

public abstract class EntityUpdater extends IdentifiableUpdater {

    protected final String name;

    public EntityUpdater(String name) {
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
