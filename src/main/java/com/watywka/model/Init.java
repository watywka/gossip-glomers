package com.watywka.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Init extends Body {

    private String nodeId;

    private List<String> nodeIds;

    public Init(@JsonProperty("msg_id") int messageId, @JsonProperty("node_id") String nodeId, @JsonProperty("node_ids") List<String> nodeIds) {
        super(messageId);
        this.nodeId = nodeId;
        this.nodeIds = nodeIds;
    }

    public List<String> getNodeIds() {
        return nodeIds;
    }

    public String getNodeId() {
        return nodeId;
    }
}
