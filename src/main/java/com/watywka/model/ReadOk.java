package com.watywka.model;

import java.util.List;

public class ReadOk extends Ok {
    private final List<Integer> messages;

    public ReadOk(int messageId, int inReplyTo, List<Integer> messages) {
        super(messageId, inReplyTo);
        this.messages = messages;
    }

    public List<Integer> getMessages() {
        return messages;
    }
}
