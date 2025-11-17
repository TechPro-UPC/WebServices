package com.techpro.upc.iam_service.infrastructure.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    /**
     * Define el nombre de la cola para los eventos de IAM (Identity and Access Management).
     */
    public static final String IAM_EVENTS = "iam.events";

    @Bean
    public Queue iamQueue() {
        // El segundo parámetro (true) indica que la cola es durable
        return new Queue(IAM_EVENTS, true);
    }
}
