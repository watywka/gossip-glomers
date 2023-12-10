package com.watywka.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BroadcastOk extends Ok {

    public BroadcastOk(@JsonProperty("msg_id") int messageId, @JsonProperty("in_reply_to") int inReplyTo) {
        super(messageId, inReplyTo);
    }
}
