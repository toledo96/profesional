package com.techi.microservices.order.service;


import com.techi.microservices.order.dto.request.OrderRequest;
import com.techi.microservices.order.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {

    public OrderResponse createOrder(OrderRequest orderRequest);

    public OrderResponse getOrderById(Long orderId);

    public List<OrderResponse> getOrders();
}
