package com.techi.microservices.inventory.controller;

import com.techi.microservices.inventory.dto.request.ProductRequest;
import com.techi.microservices.inventory.dto.response.ProductResponse;
import com.techi.microservices.inventory.model.Product;
import com.techi.microservices.inventory.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductoController {


    private final ProductoService productoService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest product) {
        ProductResponse created = productoService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // 🔹 Obtener producto por productId (NO por id de Mongo)
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(
            @RequestHeader("X-Roles") String roles,
            @PathVariable Long productId) {

        if (!roles.contains("ADMIN")) {
            throw new RuntimeException("No autorizado");
        }

        return ResponseEntity.ok(productoService.getByProductId(productId));
    }

    // 🔹 Reducir stock (evento típico desde Order)
    @PostMapping("/{productId}/reduce")
    public ResponseEntity<Void> reduceStock(
            @PathVariable Long productId,
            @RequestParam Integer quantity) {

        productoService.reduceStock(productId, quantity);
        return ResponseEntity.noContent().build();
    }

    // 🔹 Aumentar stock
    @PostMapping("/{productId}/increase")
    public ResponseEntity<Void> increaseStock(
            @PathVariable Long productId,
            @RequestParam Integer quantity) {

        productoService.increaseStock(productId, quantity);
        return ResponseEntity.noContent().build();
    }

}
