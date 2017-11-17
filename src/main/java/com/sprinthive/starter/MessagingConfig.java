package com.sprinthive.starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

@Configuration
@EnableBinding(MessagingConfig.MessageChannels.class)
public class MessagingConfig {

    @Autowired
    MessageChannels messageChannels;

    interface MessageChannels {

        @Output
        MessageChannel healthCheckMessageProducer();

        @Input
        SubscribableChannel healthCheckMessageConsumer();
    }
}
