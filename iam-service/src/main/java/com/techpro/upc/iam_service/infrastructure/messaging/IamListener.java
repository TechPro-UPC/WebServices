package com.techpro.upc.iam_service.infrastructure.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.techpro.upc.iam_service.infrastructure.configuration.RabbitConfig.IAM_EVENTS;

@Slf4j
@Component
public class IamListener {

    /**
     * Escucha mensajes en la cola "iam.events".
     * @param message El mensaje recibido
     */
    @RabbitListener(queues = IAM_EVENTS)
    public void handleIamEvent(String message) {
        log.info("[IAM] Evento recibido: {}", message);
        // Aquí iría la lógica para procesar el evento, si es necesario.
    }
}
