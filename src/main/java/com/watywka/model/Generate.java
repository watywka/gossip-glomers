package com.watywka.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Generate extends Body {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Generate(@JsonProperty("msg_id") int messageId) {
        super(messageId);
    }
}
