package com.techi.microservices.order.service.impl;

import com.techi.microservices.order.config.RabbitConfig;
import com.techi.microservices.order.event.OrderCreatedEvent;
import com.techi.microservices.order.event.OrderItemEvent;
import com.techi.microservices.order.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendOrderCreatedEvent(Order order) {
        List<OrderItemEvent> items = order.getItems().stream()
                .map(item -> new OrderItemEvent(
                        item.getProductId(),
                        item.getQuantity()))
                .toList();

        OrderCreatedEvent event = new OrderCreatedEvent(
                UUID.randomUUID().toString(), // 🔥 eventId único
                order.getId(),
                items
        );

        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.ROUTING_KEY,
                event
        );

        System.out.println("Evento enviado: " + event.getEventId());

    }

}
