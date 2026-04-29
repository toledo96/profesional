package com.techi.microservices.order.dto.request;

import com.techi.microservices.order.model.Order;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequest {

    private Long productId;

    private Integer quantity;

    private BigDecimal price;


}
