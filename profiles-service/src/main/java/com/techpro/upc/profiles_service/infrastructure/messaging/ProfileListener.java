package com.techpro.upc.profiles_service.infrastructure.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProfileListener {

    @RabbitListener(queues = "profiles.events")
    public void handleProfileEvent(String message) {
        log.info("[PROFILES] Evento recibido: {}", message);
    }
}