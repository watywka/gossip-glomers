package com.watywka;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.watywka.model.*;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        int counter = 0;
        int generateCounter = 0;
        Scanner scanner = new Scanner(System.in);
        String content = scanner.nextLine();
        System.err.println(content);
        Message<Init> init = OBJECT_MAPPER.readValue(content, new TypeReference<>() {
        });
        String node = init.getBody().getNodeId();
        List<String> nodes = init.getBody().getNodeIds();
        int offset = nodes.indexOf(node);
        InitOk initOk = new InitOk(counter++, init.getBody().getMessageId());
        Message<InitOk> initOkMessage = new Message<>(node, init.getSourceNode(), initOk);
        String output = OBJECT_MAPPER.writeValueAsString(initOkMessage);
        System.out.println(output);

        while (true) {
            content = scanner.nextLine();
            System.err.println(content);
            Message<Body> message = OBJECT_MAPPER.readValue(content, new TypeReference<>() {
            });
            Ok body;
            if (message.getBody() instanceof Echo echo) {
                body = new EchoOk(counter++, message.getBody().getMessageId(), echo.getEcho());
            } else if (message.getBody() instanceof Generate) {
                body = new GenerateOk(counter++, message.getBody().getMessageId(), offset + nodes.size() * generateCounter++);
            } else {
                return;
            }
            Message<Ok> value = new Message<>(message.getDestinationNode(), message.getSourceNode(), body);
            output = OBJECT_MAPPER.writeValueAsString(value);
            System.out.println(output);
        }
    }
}

