package com.watywka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.watywka.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Node {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PrintStream output;
    private final PrintStream debug;
    private final Scanner scanner;
    private int counter = 0;
    private int generateCounter = 0;
    private String node;
    private List<String> nodes;
    private final Set<Integer> broadcastValues = new HashSet<>();
    private final BlockingQueue<Message<Broadcast>> scheduledBroadcastMessages = new LinkedBlockingQueue<>();
    private int offset;
    private List<String> neighbourNodes;

    public Node(InputStream in, PrintStream output, PrintStream debug) {
        this.scanner = new Scanner(in);
        this.output = output;
        this.debug = debug;
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Message<Broadcast> message = scheduledBroadcastMessages.take();
                    if (broadcastValues.add(message.getBody().getMessage())) {
                        for (String nextNode : neighbourNodes) {
                            if (!nextNode.equals(message.getSourceNode())) {
                                sendMessage(message.getBody(), nextNode);
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
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
            Message<? extends Body> message = objectMapper.readValue(content, new TypeReference<>() {
            });
            Ok body = switch (message.getBody()) {
                case Echo echo -> new EchoOk(counter++, message.getBody().getMessageId(), echo.getEcho());
                case Generate ignored ->
                        new GenerateOk(counter++, message.getBody().getMessageId(), offset + nodes.size() * generateCounter++);
                case Broadcast ignored -> {
                    scheduledBroadcastMessages.add((Message<Broadcast>) message);
                    if (isNode(message.getSourceNode())) {
                        yield null;
                    }
                    yield new BroadcastOk(counter++, message.getBody().getMessageId());
                }
                case Read ignored -> new ReadOk(counter++, message.getBody().getMessageId(), broadcastValues);
                case Topology topology -> {
                    neighbourNodes = topology.getTopology().get(node);
                    yield new TopologyOk(counter++, message.getBody().getMessageId());
                }
                case Ok ignored -> null;
                case null, default -> throw new RuntimeException();
            };
            if (body != null) {
                sendMessage(body, message.getSourceNode());
            }
        }
    }

    private static boolean isNode(String name) {
        return name.charAt(0) == 'n';
    }

    private <T extends Body> void sendMessage(T body, String destination) {
        try {
            output.println(objectMapper.writeValueAsString(new Message<>(
                    node,
                    destination,
                    body
            )));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void init() throws JsonProcessingException {
        String content = scanner.nextLine();
        debug.println(content);
        Message<Init> init = objectMapper.readValue(content, new TypeReference<>() {
        });
        node = init.getBody().getNodeId();
        nodes = init.getBody().getNodeIds();
        offset = nodes.indexOf(node);
        InitOk initOk = new InitOk(counter++, init.getBody().getMessageId());
        sendMessage(initOk, init.getSourceNode());
    }
}

