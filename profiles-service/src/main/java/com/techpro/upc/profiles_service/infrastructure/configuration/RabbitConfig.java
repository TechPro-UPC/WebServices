package com.techpro.upc.profiles_service.infrastructure.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String PROFILE_EXCHANGE = "profile.exchange";
    public static final String PROFILE_CREATED_QUEUE = "profile.created.queue";
    public static final String PROFILE_CREATED_ROUTING_KEY = "profile.created";

    @Bean
    public TopicExchange profileExchange() {
        return new TopicExchange(PROFILE_EXCHANGE);
    }

    @Bean
    public Queue profileCreatedQueue() {
        // durable = true para que la cola sobreviva reinicios del broker
        return new Queue(PROFILE_CREATED_QUEUE, true);
    }

    @Bean
    public Binding profileCreatedBinding() {
        return BindingBuilder
                .bind(profileCreatedQueue())
                .to(profileExchange())
                .with(PROFILE_CREATED_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jacksonMessageConverter() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return new Jackson2JsonMessageConverter(mapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}