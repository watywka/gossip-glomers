package com.watywka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.watywka.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Node {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PrintStream output;
    private final PrintStream debug;
    private final Scanner scanner;
    private int counter = 0;
    private int generateCounter = 0;
    private String node;
    private List<String> nodes;
    private final List<Integer> broadcastValues = new ArrayList<>();
    private int offset;

    public Node(InputStream in, PrintStream output, PrintStream debug) {
        this.scanner = new Scanner(in);
        this.output = output;
        this.debug = debug;
    }

    public static void main(String[] args) throws IOException {
        Node node = new Node(System.in, System.out, System.err);
        node.start();
    }

    private void start() throws JsonProcessingException {
        init();
        String content;

        while (true) {
            content = scanner.nextLine();
            debug.println(content);
            Message<Body> message = objectMapper.readValue(content, new TypeReference<>() {
            });
            Ok body = switch (message.getBody()) {
                case Echo echo ->  new EchoOk(counter++, message.getBody().getMessageId(), echo.getEcho());
                case Generate ignored -> new GenerateOk(counter++, message.getBody().getMessageId(), offset + nodes.size() * generateCounter++);
                case Broadcast broadcast -> {
                    broadcastValues.add(broadcast.getMessage());
                    yield new BroadcastOk(counter++, message.getBody().getMessageId());
                }
                case Read ignored -> new ReadOk(counter++, message.getBody().getMessageId(), broadcastValues);
                case Topology ignored -> new TopologyOk(counter++, message.getBody().getMessageId());
                case null, default -> throw new RuntimeException();
            };
            Message<Ok> value = new Message<>(message.getDestinationNode(), message.getSourceNode(), body);
            output.println(objectMapper.writeValueAsString(value));
        }
    }

    private void init() throws JsonProcessingException {
        String content = scanner.nextLine();
        debug.println(content);
        Message<Init> init = objectMapper.readValue(content, new TypeReference<>() {});
        node = init.getBody().getNodeId();
        nodes = init.getBody().getNodeIds();
        offset = nodes.indexOf(node);
        InitOk initOk = new InitOk(counter++, init.getBody().getMessageId());
        Message<InitOk> initOkMessage = new Message<>(node, init.getSourceNode(), initOk);
        output.println(objectMapper.writeValueAsString(initOkMessage));
    }
}

