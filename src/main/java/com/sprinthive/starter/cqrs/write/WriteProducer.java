package com.sprinthive.starter.cqrs.write;

import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WriteProducer {

    private final MessageChannel messageChannel;

    public WriteProducer(MessageChannel messageChannel) {
        this.messageChannel = messageChannel;
    }

    public void recordFact(String entityKey, String entityId, String action, Map payload) {
        WritePayload  finalPayload = WritePayload.builder()
                .entityKey(entityKey)
                .entityId(entityId)
                .action(action)
                .payload(payload)
                .build();
        messageChannel.send(MessageBuilder
                .withPayload(finalPayload)
                .build());
    }
}
