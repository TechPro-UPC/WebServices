package com.techpro.upc.catalog_service.infrastructure.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    /**
     * Define el nombre de la cola para los eventos de catálogo.
     * Es una buena práctica usar un nombre consistente, como "catalog.events".
     */
    public static final String CATALOG_EVENTS = "catalog.events";

    @Bean
    public Queue catalogQueue() {
        // El segundo parámetro (true) indica que la cola es durable
        // (sobrevivirá a reinicios del broker RabbitMQ)
        return new Queue(CATALOG_EVENTS, true);
    }
}
