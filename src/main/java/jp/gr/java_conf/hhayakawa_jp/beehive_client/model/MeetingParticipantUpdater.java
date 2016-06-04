package jp.gr.java_conf.hhayakawa_jp.beehive_client.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import jp.gr.java_conf.hhayakawa_jp.beehive_client.BeehiveApiPayload;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class MeetingParticipantUpdater
    extends BaseParticipantUpdater implements BeehiveApiPayload {

    private final String beeType = "meetingParticipantUpdater";
    private final String address;
    private final String name;
    private final MeetingParticipantUpdaterOperation operation;
    private final BeeId participant;

    public MeetingParticipantUpdater(String address, String name, MeetingParticipantUpdaterOperation operation,
            BeeId participant) {
        super();
        this.address = address;
        this.name = name;
        this.operation = operation;
        this.participant = participant;
    }

    public String getBeeType() {
        return beeType;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public MeetingParticipantUpdaterOperation getOperation() {
        return operation;
    }

    public BeeId getParticipant() {
        return participant;
    }

}

