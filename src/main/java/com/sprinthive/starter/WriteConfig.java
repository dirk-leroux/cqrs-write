package com.sprinthive.starter;

import com.sprinthive.starter.cqrs.write.WriteProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WriteConfig {

    @Autowired
    MessagingConfig.MessageChannels messageChannels;

    @Bean
    public WriteProducer writeProducer(BinderAwareChannelResolver resolver) {
        return new WriteProducer(messageChannels.cqrsWriteProducer(), resolver);
    }

}
