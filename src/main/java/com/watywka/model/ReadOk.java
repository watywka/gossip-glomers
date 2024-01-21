package com.watywka.model;

import java.util.Set;

public class ReadOk extends Ok {
    private final Set<Integer> messages;

    public ReadOk(int messageId, int inReplyTo, Set<Integer> messages) {
        super(messageId, inReplyTo);
        this.messages = messages;
    }

    public Set<Integer> getMessages() {
        return messages;
    }
}
