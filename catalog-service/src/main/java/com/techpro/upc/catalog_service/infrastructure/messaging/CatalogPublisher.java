package com.techpro.upc.catalog_service.infrastructure.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.techpro.upc.catalog_service.infrastructure.configuration.RabbitConfig.CATALOG_EVENTS;

@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogPublisher {

    // Spring Boot configura automáticamente este template
    private final RabbitTemplate rabbitTemplate;

    /**
     * Envía un mensaje a la cola de eventos de catálogo.
     * @param message El mensaje (usualmente un JSON serializado)
     */
    public void sendCatalogEvent(String message) {
        log.info("[CATALOG] Enviando evento: {}", message);
        // Envía el mensaje a la cola definida en RabbitConfig
        rabbitTemplate.convertAndSend(CATALOG_EVENTS, message);
    }
}
