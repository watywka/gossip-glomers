package com.watywka.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EchoOk extends Ok {

    @JsonProperty("echo")
    public final String echo;

    public EchoOk(int messageId, int inReplyTo, String echo) {
        super(messageId, inReplyTo);
        this.echo = echo;
    }
}
