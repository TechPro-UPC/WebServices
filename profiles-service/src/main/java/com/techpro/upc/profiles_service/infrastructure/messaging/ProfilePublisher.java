package com.techpro.upc.profiles_service.infrastructure.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import static com.techpro.upc.profiles_service.infrastructure.configuration.RabbitConfig.PROFILE_EVENTS;

@Service
@RequiredArgsConstructor
public class ProfilePublisher {

    private final RabbitTemplate rabbitTemplate;

    public void sendProfileEvent(String message) {
        rabbitTemplate.convertAndSend(PROFILE_EVENTS, message);
    }
}