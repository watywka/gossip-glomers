package com.watywka.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Read extends Body {

    public Read(@JsonProperty("msg_id") int messageId) {
        super(messageId);
    }
}
