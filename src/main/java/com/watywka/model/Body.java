package com.watywka.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Init.class, name = "init"),
        @JsonSubTypes.Type(value = InitOk.class, name = "init_ok"),
        @JsonSubTypes.Type(value = Echo.class, name = "echo"),
        @JsonSubTypes.Type(value = EchoOk.class, name = "echo_ok"),
        @JsonSubTypes.Type(value = Generate.class, name = "generate"),
        @JsonSubTypes.Type(value = GenerateOk.class, name = "generate_ok"),
        @JsonSubTypes.Type(value = Broadcast.class, name = "broadcast"),
        @JsonSubTypes.Type(value = BroadcastOk.class, name = "broadcast_ok"),
        @JsonSubTypes.Type(value = Read.class, name = "read"),
        @JsonSubTypes.Type(value = ReadOk.class, name = "read_ok"),
        @JsonSubTypes.Type(value = Topology.class, name = "topology"),
        @JsonSubTypes.Type(value = TopologyOk.class, name = "topology_ok")
})
public abstract class Body {
    @JsonProperty("msg_id")
    private final int messageId;

    public Body(int messageId) {
        this.messageId = messageId;
    }

    public int getMessageId() {
        return messageId;
    }
}
