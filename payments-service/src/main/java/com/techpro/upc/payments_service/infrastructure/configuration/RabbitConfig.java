package com.techpro.upc.payments_service.infrastructure.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    /**
     * Define el nombre de la cola para los eventos de Pagos (Payments).
     */
    public static final String PAYMENTS_EVENTS = "payments.events";

    @Bean
    public Queue paymentsQueue() {
        // El segundo parámetro (true) indica que la cola es durable
        return new Queue(PAYMENTS_EVENTS, true);
    }
}
