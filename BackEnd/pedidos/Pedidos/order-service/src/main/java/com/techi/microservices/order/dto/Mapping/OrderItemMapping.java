package com.techi.microservices.order.dto.Mapping;

import com.techi.microservices.order.dto.request.OrderItemRequest;
import com.techi.microservices.order.dto.request.OrderRequest;
import com.techi.microservices.order.dto.response.OrderItemResponse;
import com.techi.microservices.order.dto.response.OrderResponse;
import com.techi.microservices.order.model.Order;
import com.techi.microservices.order.model.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

public class OrderItemMapping {

    public static OrderItemResponse toDto(OrderItem orderItem){
        OrderItemResponse orderItemResponse = OrderItemResponse.builder()
                .productId(orderItem.getProductId())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .build();

        return orderItemResponse;
    }

    public static OrderItem toEntity(OrderItemRequest orderItemRequest){
        OrderItem item = OrderItem.builder()
                .productId(orderItemRequest.getProductId())
                .price(orderItemRequest.getPrice())
                .quantity(orderItemRequest.getQuantity())
                .build();

        return item;
    }

    public static List<OrderItemResponse> toDtoList(List<OrderItem> items){
        if (items == null) return List.of();
        return items.stream().map(OrderItemMapping::toDto).toList();
    }

    public static List<OrderItem> toEntityList(List<OrderItemRequest> items){
        if (items == null) return List.of();
        return items.stream().map(OrderItemMapping::toEntity).collect(Collectors.toList());
    }
}
