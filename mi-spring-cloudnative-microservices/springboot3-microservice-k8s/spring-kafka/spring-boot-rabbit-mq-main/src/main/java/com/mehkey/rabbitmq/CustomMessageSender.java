package com.mehkey.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CustomMessageSender {

    private static final Logger log = LoggerFactory.getLogger(CustomMessageSender.class);

    private final RabbitTemplate rabbitTemplate;

    public CustomMessageSender(final RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedDelay = 3000L)
    public void sendMessage() {
        final var message = new CustomMessage("Hello World", new Random().nextInt(30), false);
        log.info("Sending message...");
        rabbitTemplate.convertAndSend(RabbitmqSpringBootApplication.EXCHANGE_NAME, RabbitmqSpringBootApplication.ROUTING_KEY, message);
    }
}
