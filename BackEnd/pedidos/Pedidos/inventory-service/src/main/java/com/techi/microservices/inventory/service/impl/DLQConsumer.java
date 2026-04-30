package com.techi.microservices.inventory.service.impl;

import com.techi.microservices.inventory.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class DLQConsumer {
    @RabbitListener(queues = RabbitConfig.DLQ)
    public void handleFailedMessage(Object message) {
        System.err.println("Mensaje en DLQ: " + message);
    }
}