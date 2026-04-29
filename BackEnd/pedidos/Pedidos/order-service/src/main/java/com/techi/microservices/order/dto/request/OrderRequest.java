package com.techi.microservices.order.dto.request;


import com.techi.microservices.order.model.OrderItem;
import com.techi.microservices.order.model.OrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private Long userId;

    private BigDecimal total;

    List<OrderItemRequest> items = new ArrayList<>();
}
