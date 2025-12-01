package com.techpro.upc.profiles_service.infrastructure.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    // Debe llamarse igual que en el IAM
    public static final String EXCHANGE_NAME = "iam.users.exchange";
    public static final String QUEUE_NAME = "profiles.creation.queue";

    @Bean
    public TopicExchange usersExchange() { return new TopicExchange(EXCHANGE_NAME); }

    @Bean
    public Queue profilesQueue() { return new Queue(QUEUE_NAME, true); }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        // Escuchamos cualquier evento de registro (*.registered)
        return BindingBuilder.bind(queue).to(exchange).with("*.registered");
    }

    // IMPORTANTE: Converter para leer el JSON que manda IAM
    @Bean
    public org.springframework.amqp.support.converter.MessageConverter jsonMessageConverter() {
        return new org.springframework.amqp.support.converter.Jackson2JsonMessageConverter();
    }
}
