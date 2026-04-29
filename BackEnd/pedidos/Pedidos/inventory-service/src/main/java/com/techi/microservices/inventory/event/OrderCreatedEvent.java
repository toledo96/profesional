package com.techi.microservices.inventory.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEvent {

    private String eventId; //  clave para idempotencia
    private Long orderId;
    private List<OrderItemEvent> items;
}