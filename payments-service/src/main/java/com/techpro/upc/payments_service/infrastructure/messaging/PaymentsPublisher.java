package com.techpro.upc.payments_service.infrastructure.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.techpro.upc.payments_service.infrastructure.configuration.RabbitConfig.PAYMENTS_EVENTS;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentsPublisher {

    private final RabbitTemplate rabbitTemplate;

    /**
     * Envía un mensaje a la cola de eventos de Pagos.
     * @param message El mensaje (usualmente un JSON serializado)
     */
    public void sendPaymentsEvent(String message) {
        // Siguiendo las buenas prácticas
        log.info("[PAYMENTS] Enviando evento: {}", message);
        rabbitTemplate.convertAndSend(PAYMENTS_EVENTS, message);
    }
}
