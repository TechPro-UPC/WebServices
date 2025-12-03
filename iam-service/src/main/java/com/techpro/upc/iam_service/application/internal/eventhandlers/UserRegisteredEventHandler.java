package com.techpro.upc.iam_service.application.internal.eventhandlers;

import com.techpro.upc.iam_service.domain.model.events.UserRegisteredEvent;
import com.techpro.upc.iam_service.infrastructure.configuration.RabbitMqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class UserRegisteredEventHandler {
    private final RabbitTemplate rabbitTemplate;

    public UserRegisteredEventHandler(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Async // Para no bloquear el hilo principal del registro
    @EventListener
    public void on(UserRegisteredEvent event) {
        System.out.println("IAM: Enviando evento a RabbitMQ para usuario " + event.getEmail());
        // Routing key: "rol.registered". Ej: "PATIENT.registered"
        String routingKey = event.getRole().name() + ".registered";
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME, routingKey, event);
    }
}
