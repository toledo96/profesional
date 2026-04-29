package com.techi.microservices.inventory.service.impl;

import com.mongodb.DuplicateKeyException;
import com.techi.microservices.inventory.config.RabbitConfig;
import com.techi.microservices.inventory.event.OrderCreatedEvent;
import com.techi.microservices.inventory.event.OrderItemEvent;
import com.techi.microservices.inventory.model.ProcessedEvent;
import com.techi.microservices.inventory.repository.ProcessedEventRepository;
import com.techi.microservices.inventory.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderConsumer {

    private final ProductoService productoService;
    private final ProcessedEventRepository processedEventRepository;

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void handleOrderCreated(OrderCreatedEvent event){

        //  Validación básica
        if (event.getItems() == null || event.getItems().isEmpty()) {
            return;
        }

        // INTENTO de registrar el evento (clave idempotencia)
        try {
            processedEventRepository.save(
                    ProcessedEvent.builder()
                            .eventId(event.getEventId())
                            .orderId(event.getOrderId())
                            .build()
            );
        } catch (DuplicateKeyException e) {
            // Si ya fue procesado antes → ignorar
            System.out.println("Evento duplicado ignorado: " + event.getEventId());
            return;
        }

        // 🔹 Procesamiento real
        for (OrderItemEvent item : event.getItems()) {
            productoService.reduceStock(
                    item.getProductId(),
                    item.getQuantity()
            );
        }

        System.out.println("Evento procesado correctamente: " + event.getEventId());
    }

}
