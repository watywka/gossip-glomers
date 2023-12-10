package com.watywka.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class Topology extends Body {
    private final Map<String, List<Integer>> topology;

    public Topology(@JsonProperty("msg_id") int messageId, @JsonProperty("topology") Map<String, List<Integer>> topology) {
        super(messageId);
        this.topology = topology;
    }

    public Map<String, List<Integer>> getTopology() {
        return topology;
    }
}
