package jp.gr.java_conf.hhiroshell.beehive4j.model;

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
