package com.sprinthive.starter.cqrs.write;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.json.JsonParser;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Collections;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WriteProducer {

    private final MessageChannel messageChannel;
    private final BinderAwareChannelResolver resolver;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public WriteProducer(MessageChannel messageChannel, BinderAwareChannelResolver resolver) {
        this.messageChannel = messageChannel;
        this.resolver = resolver;
    }

    public void recordFact(String entityKey, String entityId, String action, Map payload) throws JsonProcessingException {
        WritePayload  finalPayload = WritePayload.builder()
                .entityKey(entityKey)
                .entityId(entityId)
                .action(action)
                .payload(payload)
                .build();
//        messageChannel.send(MessageBuilder
//                .withPayload(finalPayload)
//                .setHeader("entityKey", entityKey)
//                .setHeader("entityId", entityId)
//                .setHeader("action", action)
//                .build());
        MessageChannel messageChannel = resolver.resolveDestination("cqrs." + entityKey + "." + action);
        String strPayload = objectMapper.writeValueAsString(finalPayload);
        messageChannel.send(MessageBuilder
                .withPayload(strPayload)
                .setHeader(MessageHeaders.CONTENT_TYPE, "application/json")
                .setHeader("entityKey", entityKey)
                .setHeader("entityId", entityId)
                .setHeader("action", action)
                .build());
    }
}
