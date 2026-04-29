package com.techi.microservices.order.dto.response;

import com.techi.microservices.order.model.OrderItem;
import com.techi.microservices.order.model.OrderStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private BigDecimal total;

    List<OrderItemResponse> items = new ArrayList<>();
}
