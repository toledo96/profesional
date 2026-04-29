package com.techi.microservices.order.dto.Mapping;

import com.techi.microservices.order.dto.request.OrderRequest;
import com.techi.microservices.order.dto.response.OrderItemResponse;
import com.techi.microservices.order.dto.response.OrderResponse;
import com.techi.microservices.order.model.Order;
import com.techi.microservices.order.model.OrderItem;

import java.util.List;

public class OrderMapping {

    public static OrderResponse toDto(Order order){
        List<OrderItemResponse> items = OrderItemMapping.toDtoList(order.getItems());

        OrderResponse orderResponse = OrderResponse.builder()
                .status(order.getStatus())
                .total(order.getTotal())
                .items(items)
                .build();
        return orderResponse;
    }

    public static Order toEntity(OrderRequest orderRequest){
        Order order = Order.builder()
                .userId(orderRequest.getUserId())
                .total(orderRequest.getTotal())
                .build();

        List<OrderItem> items = OrderItemMapping.toEntityList(orderRequest.getItems());
        items.forEach(order::addItem);

        return order;
    }


}
