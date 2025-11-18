package com.techpro.upc.scheduling_service.infrastructure.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.techpro.upc.scheduling_service.infrastructure.configuration.RabbitConfig.SCHEDULING_EVENTS;

@Slf4j
@Component
public class SchedulingListener {

    /**
     * Escucha mensajes en la cola "scheduling.events".
     * @param message El mensaje recibido
     */
    @RabbitListener(queues = SCHEDULING_EVENTS)
    public void handleSchedulingEvent(String message) {
        log.info("[SCHEDULING] Evento recibido: {}", message);
        // Aquí iría la lógica para procesar el evento (ej. reservar un timeslot)
    }
}
