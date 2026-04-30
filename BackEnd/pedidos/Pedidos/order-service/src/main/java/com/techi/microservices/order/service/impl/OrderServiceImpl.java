package com.techi.microservices.order.service.impl;

import com.techi.microservices.order.dto.Mapping.OrderMapping;
import com.techi.microservices.order.dto.request.OrderRequest;
import com.techi.microservices.order.dto.response.OrderResponse;
import com.techi.microservices.order.exception.ItemsNotFoundException;
import com.techi.microservices.order.exception.OrderNotFoundException;
import com.techi.microservices.order.model.Order;
import com.techi.microservices.order.model.OrderStatus;
import com.techi.microservices.order.repository.OrderRepository;
import com.techi.microservices.order.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    @Transactional
    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        Order order = OrderMapping.toEntity(orderRequest);

        if(order.getItems() == null || order.getItems().isEmpty()){
            throw new ItemsNotFoundException("Items no encontrados");
        }

        order.setStatus(OrderStatus.CREATED);

        // calcular total
        BigDecimal total = order.getItems().stream().map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);

        order.setTotal(total);

        Order saved = orderRepository.save(order);


        try {
            // Evento con RabbitMQ para publicar
            orderProducer.sendOrderCreatedEvent(saved);
        } catch (Exception e) {
            // loggear error
            log.info("error al enviar evento: {}" , e.getMessage());
        }

        // Evento con RabbitMQ para publicar


        return OrderMapping.toDto(saved);
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        OrderResponse orderResponse = OrderMapping.toDto(orderRepository.findById(orderId).
                orElseThrow(() -> new OrderNotFoundException("Orden no encontrada con id: " + orderId)));
        return orderResponse;
    }

    @Override
    public List<OrderResponse> getOrders() {
        return orderRepository.findAll().stream().map(OrderMapping::toDto).toList();
    }
}
