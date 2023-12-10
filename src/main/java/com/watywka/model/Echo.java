package com.watywka.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Echo extends Body {
    private final String echo;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Echo(@JsonProperty("msg_id") int messageId, @JsonProperty("echo") String echo) {
        super(messageId);
        this.echo = echo;
    }

    public String getEcho() {
        return echo;
    }
}
