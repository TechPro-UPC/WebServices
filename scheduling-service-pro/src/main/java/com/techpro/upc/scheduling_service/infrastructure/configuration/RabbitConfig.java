package com.techpro.upc.scheduling_service.infrastructure.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    /**
     * Define el nombre de la cola para los eventos de Programación (Scheduling).
     */
    public static final String SCHEDULING_EVENTS = "scheduling.events";

    @Bean
    public Queue schedulingQueue() {
        // El segundo parámetro (true) indica que la cola es durable
        return new Queue(SCHEDULING_EVENTS, true);
    }
}
