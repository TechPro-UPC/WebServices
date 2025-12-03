package com.techpro.upc.reviews_service.application.internal.eventhandlers;

import com.techpro.upc.reviews_service.domain.model.events.PsychologistReviewedEvent;
import com.techpro.upc.reviews_service.infrastructure.configuration.RabbitMqConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ReviewCreatedEventHandler {

    private final RabbitTemplate rabbitTemplate;

    public ReviewCreatedEventHandler(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Async
    @EventListener
    public void on(PsychologistReviewedEvent event) {
        System.out.println("Reviews: Publicando review para psicólogo " + event.getPsychologistId());
        // Routing Key: "psychologist.reviewed"
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME, "psychologist.reviewed", event);
    }
}
