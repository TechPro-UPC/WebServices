package com.techpro.upc.scheduling_service.infrastructure.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.techpro.upc.scheduling_service.infrastructure.configuration.RabbitConfig.SCHEDULING_EVENTS;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulingPublisher {

    private final RabbitTemplate rabbitTemplate;

    /**
     * Envía un mensaje a la cola de eventos de Programación.
     * @param message El mensaje (usualmente un JSON serializado)
     */
    public void sendSchedulingEvent(String message) {
        // Siguiendo las buenas prácticas
        log.info("[SCHEDULING] Enviando evento: {}", message);
        rabbitTemplate.convertAndSend(SCHEDULING_EVENTS, message);
    }
}
