package com.techi.microservices.order.controller;


import com.techi.microservices.order.dto.request.OrderRequest;
import com.techi.microservices.order.dto.response.OrderResponse;
import com.techi.microservices.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest orderRequest){
        OrderResponse response = orderService.createOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrders(){
        List<OrderResponse> orderResponseList = orderService.getOrders();
        return ResponseEntity.ok(orderResponseList);
    }

    @GetMapping("/{idOrder}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long idOrder){
        OrderResponse orderResponse = orderService.getOrderById(idOrder);
        return ResponseEntity.status(HttpStatus.OK).body(orderResponse);
    }

}
