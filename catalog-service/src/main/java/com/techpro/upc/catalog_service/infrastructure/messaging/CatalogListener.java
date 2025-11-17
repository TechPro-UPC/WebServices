package com.techpro.upc.catalog_service.infrastructure.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.techpro.upc.catalog_service.infrastructure.configuration.RabbitConfig.CATALOG_EVENTS;

@Slf4j
@Component
public class CatalogListener {

    /**
     * Escucha mensajes en la cola "catalog.events".
     * @param message El mensaje recibido
     */
    @RabbitListener(queues = CATALOG_EVENTS)
    public void handleCatalogEvent(String message) {
        // Por ahora, solo registramos el evento.
        // Aquí iría la lógica para procesar el evento.
        log.info("[CATALOG] Evento recibido: {}", message);
    }
}