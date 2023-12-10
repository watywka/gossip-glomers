package com.watywka.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Ok extends Body {

    @JsonProperty("in_reply_to")
    private final int inReplyTo;

    public Ok(int messageId, int inReplyTo) {
        super(messageId);
        this.inReplyTo = inReplyTo;
    }

    public int getInReplyTo() {
        return inReplyTo;
    }

}
