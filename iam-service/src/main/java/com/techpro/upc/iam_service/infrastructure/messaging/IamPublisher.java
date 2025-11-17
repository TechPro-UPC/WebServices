package com.techpro.upc.iam_service.infrastructure.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.techpro.upc.iam_service.infrastructure.configuration.RabbitConfig.IAM_EVENTS;

@Slf4j
@Service
@RequiredArgsConstructor
public class IamPublisher {

    private final RabbitTemplate rabbitTemplate;

    /**
     * Envía un mensaje a la cola de eventos de IAM.
     * @param message El mensaje (usualmente un JSON serializado)
     */
    public void sendIamEvent(String message) {
        // Siguiendo las buenas prácticas, añadimos el log
        log.info("[IAM] Enviando evento: {}", message);
        rabbitTemplate.convertAndSend(IAM_EVENTS, message);
    }
}
