package com.watywka.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Broadcast extends Body {
    private final int message;

    public Broadcast(@JsonProperty("msg_id") int messageId, @JsonProperty("message") int message) {
        super(messageId);
        this.message = message;
    }

    public int getMessage() {
        return message;
    }
}
