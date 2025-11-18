package com.techpro.upc.reviews_service.infrastructure.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    /**
     * Define el nombre de la cola para los eventos de Reseñas (Reviews).
     */
    public static final String REVIEWS_EVENTS = "reviews.events";

    @Bean
    public Queue reviewsQueue() {
        // El segundo parámetro (true) indica que la cola es durable
        return new Queue(REVIEWS_EVENTS, true);
    }
}
