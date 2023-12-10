package com.watywka.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties("id")
public class Message<T extends Body> {

    @JsonProperty("src")
    private final String sourceNode;

    @JsonProperty("dest")
    private final String destinationNode;

    private final T body;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Message(@JsonProperty("src") String sourceNode, @JsonProperty("dest") String destinationNode, @JsonProperty("body") T body) {
        this.sourceNode = sourceNode;
        this.destinationNode = destinationNode;
        this.body = body;
    }

    public String getSourceNode() {
        return sourceNode;
    }

    public String getDestinationNode() {
        return destinationNode;
    }

    public T getBody() {
        return body;
    }
}
