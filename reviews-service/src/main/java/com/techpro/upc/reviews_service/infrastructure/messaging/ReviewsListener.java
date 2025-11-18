package com.techpro.upc.reviews_service.infrastructure.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.techpro.upc.reviews_service.infrastructure.configuration.RabbitConfig.REVIEWS_EVENTS;

@Slf4j
@Component
public class ReviewsListener {

    /**
     * Escucha mensajes en la cola "reviews.events".
     * @param message El mensaje recibido
     */
    @RabbitListener(queues = REVIEWS_EVENTS)
    public void handleReviewsEvent(String message) {
        log.info("[REVIEWS] Evento recibido: {}", message);
        // Aquí iría la lógica para procesar el evento (ej. calcular un rating promedio)
    }
}
