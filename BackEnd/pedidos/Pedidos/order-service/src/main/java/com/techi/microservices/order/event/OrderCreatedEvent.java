package com.techi.microservices.order.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEvent {

    private String eventId; //  identificador único del evento
    private Long orderId;
    private List<OrderItemEvent> items;

}