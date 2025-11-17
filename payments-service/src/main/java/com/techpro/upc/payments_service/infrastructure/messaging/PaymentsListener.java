package com.techpro.upc.payments_service.infrastructure.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.techpro.upc.payments_service.infrastructure.configuration.RabbitConfig.PAYMENTS_EVENTS;

@Slf4j
@Component
public class PaymentsListener {

    /**
     * Escucha mensajes en la cola "payments.events".
     * @param message El mensaje recibido
     */
    @RabbitListener(queues = PAYMENTS_EVENTS)
    public void handlePaymentsEvent(String message) {
        log.info("[PAYMENTS] Evento recibido: {}", message);
        // Aquí iría la lógica para procesar el evento (ej. confirmar un pago)
    }
}
