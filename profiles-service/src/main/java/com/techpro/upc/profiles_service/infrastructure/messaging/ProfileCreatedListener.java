package com.techpro.upc.profiles_service.infrastructure.messaging;


import com.techpro.upc.profiles_service.infrastructure.configuration.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProfileCreatedListener {

    @RabbitListener(queues = RabbitConfig.PROFILE_CREATED_QUEUE)
    public void listen(ProfileCreatedMessage message) {
        log.info("[profiles-service] Mensaje recibido desde RabbitMQ: {}", message);
    }
}
