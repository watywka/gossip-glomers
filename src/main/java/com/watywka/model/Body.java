package com.watywka.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Init.class, name = "init"),
        @JsonSubTypes.Type(value = InitOk.class, name = "init_ok"),
        @JsonSubTypes.Type(value = Echo.class, name = "echo"),
        @JsonSubTypes.Type(value = EchoOk.class, name = "echo_ok")
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
