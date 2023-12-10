package com.watywka.model;

public class GenerateOk extends Ok {
    private final int id;

    public GenerateOk(int messageId, int inReplyTo, int id) {
        super(messageId, inReplyTo);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
