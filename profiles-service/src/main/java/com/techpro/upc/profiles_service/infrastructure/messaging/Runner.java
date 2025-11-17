package com.techpro.upc.profiles_service.infrastructure.messaging;

import java.util.concurrent.TimeUnit;

import com.techpro.upc.profiles_service.infrastructure.configuration.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(
                RabbitConfig.TOPIC_EXCHANGE_NAME,
                "foo.bar.baz",
                "Hello from RabbitMQ!"
        );
        receiver.getLatch().await(10_000, TimeUnit.MILLISECONDS);
    }
}
