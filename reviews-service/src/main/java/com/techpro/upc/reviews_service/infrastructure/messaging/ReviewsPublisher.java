package com.techpro.upc.reviews_service.infrastructure.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.techpro.upc.reviews_service.infrastructure.configuration.RabbitConfig.REVIEWS_EVENTS;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewsPublisher {

    private final RabbitTemplate rabbitTemplate;

    /**
     * Envía un mensaje a la cola de eventos de Reseñas.
     * @param message El mensaje (usualmente un JSON serializado)
     */
    public void sendReviewsEvent(String message) {
        // Siguiendo las buenas prácticas
        log.info("[REVIEWS] Enviando evento: {}", message);
        rabbitTemplate.convertAndSend(REVIEWS_EVENTS, message);
    }
}
