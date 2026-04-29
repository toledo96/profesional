package com.techi.microservices.inventory.service.impl;

import com.techi.microservices.inventory.model.Product;
import com.techi.microservices.inventory.repository.ProductRepository;
import com.techi.microservices.inventory.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductoService {

    private final ProductRepository productRepository;

    @Override
    public void reduceStock(Long productId, Integer quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Cantidad inválida");
        }

        Product product = productRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + productId));

        if (product.getStock() < quantity) {
            throw new RuntimeException("Stock insuficiente");
        }

        product.setStock(product.getStock() - quantity);

        productRepository.save(product);
    }

    @Override
    public void increaseStock(Long productId, Integer quantity) {

        if (quantity <= 0) {
            throw new IllegalArgumentException("Cantidad inválida");
        }

        Product product = productRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + productId));

        product.setStock(product.getStock() + quantity);

        productRepository.save(product);
    }

    @Override
    public Product getByProductId(Long productId) {
        return productRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + productId));
    }

    @Override
    public Product createProduct(Product product) {
        Product p =productRepository.save(product);
        return p;
    }
}