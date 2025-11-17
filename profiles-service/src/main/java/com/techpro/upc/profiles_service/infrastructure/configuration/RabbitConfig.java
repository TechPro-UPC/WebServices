package com.techpro.upc.profiles_service.infrastructure.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String PROFILE_EVENTS = "profiles.events";

    @Bean
    public Queue profilesQueue() {
        return new Queue(PROFILE_EVENTS, true);
    }
}