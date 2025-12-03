package com.techpro.upc.profiles_service.application.internal.eventhandlers;

import com.techpro.upc.profiles_service.domain.model.events.PsychologistProfileUpdatedEvent;
import com.techpro.upc.profiles_service.infrastructure.configuration.RabbitMqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class PsychologistProfileUpdatedEventHandler {

    private final RabbitTemplate rabbitTemplate;

    public PsychologistProfileUpdatedEventHandler(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Async
    @EventListener
    public void on(PsychologistProfileUpdatedEvent event) {
        System.out.println("Profiles: Publicando actualización de psicólogo ID " + event.getPsychologistId());
        // Routing Key: "psychologist.updated"
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME, "psychologist.updated", event);
    }
}
