package com.watywka;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        int counter = 0;
        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        Scanner scanner = new Scanner(System.in);
        String content = scanner.nextLine();
        System.err.println(content);
        Message<Init> init = objectMapper.readValue(content, new TypeReference<>() {
        });
        String node = init.body.nodeId;
        List<String> nodes = init.body.nodeIds;
        InitOk initOk = new InitOk();
        initOk.inReplyTo = init.body.messageId;
        initOk.messageId = counter++;
        Message<InitOk> initOkMessage = new Message<>();
        initOkMessage.body = initOk;
        initOkMessage.src = node;
        initOkMessage.dest = init.src;
        String output = objectMapper.writeValueAsString(initOkMessage);
        System.out.println(output);

        while (true) {
            Message<Echo> message = objectMapper.readValue(scanner.nextLine(), new TypeReference<>() {
            });
            Message<EchoOk> value = new Message<>();
            value.src = message.dest;
            value.dest = message.src;
            value.body = new EchoOk();
            value.body.messageId = counter++;
            value.body.inReplyTo = message.body.messageId;
            value.body.echo = message.body.echo;
            output = objectMapper.writeValueAsString(value);
            System.out.println(output);
        }
    }
}

@JsonIgnoreProperties("id")
class Message<T extends Body> {
    //    public String id;
    public String src;
    public String dest;
    public T body;

}

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Init.class, name = "init"),
        @JsonSubTypes.Type(value = InitOk.class, name = "init_ok"),
        @JsonSubTypes.Type(value = Echo.class, name = "echo"),
        @JsonSubTypes.Type(value = EchoOk.class, name = "echo_ok")
})
class Body {
    @JsonProperty("msg_id")
    public int messageId;
}

class Init extends Body {
    @JsonProperty("node_id")
    public String nodeId;
    @JsonProperty("node_ids")
    public List<String> nodeIds;
}

abstract class Ok extends Body {
    @JsonProperty("in_reply_to")
    public int inReplyTo;

}

class InitOk extends Ok {
}

class Echo extends Body {
    public String echo;
}

class EchoOk extends Ok {
    public String echo;
}