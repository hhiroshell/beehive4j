package jp.gr.java_conf.hhayakawa_jp.beehive4j.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class MeetingParticipantUpdater extends BaseParticipantUpdater {

    private final String beeType = "meetingParticipantUpdater";
    private final String address;
    private final String name;
    private final MeetingParticipantUpdaterOperation operation;
    private final BeeId participant;

    public static class Builder {
        private String address;
        private String name;
        private MeetingParticipantUpdaterOperation operation;
        private BeeId participant;

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder meetingParticipantUpdaterOperation(
                MeetingParticipantUpdaterOperation operation) {
            this.operation = operation;
            return this;
        }

        public Builder beeId(BeeId participant) {
            this.participant = participant;
            return this;
        }

        public MeetingParticipantUpdater build() {
            return new MeetingParticipantUpdater(
                    this.address,
                    this.name,
                    this.operation,
                    this.participant);
        }
    }

    private MeetingParticipantUpdater(String address, String name,
            MeetingParticipantUpdaterOperation operation, BeeId participant) {
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

