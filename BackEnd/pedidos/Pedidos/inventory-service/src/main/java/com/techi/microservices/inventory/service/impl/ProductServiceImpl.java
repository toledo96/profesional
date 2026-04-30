package com.techi.microservices.inventory.service.impl;

import com.techi.microservices.inventory.dto.mapping.ProductMapping;
import com.techi.microservices.inventory.dto.request.ProductRequest;
import com.techi.microservices.inventory.dto.response.ProductResponse;
import com.techi.microservices.inventory.model.Product;
import com.techi.microservices.inventory.repository.ProductRepository;
import com.techi.microservices.inventory.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
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

        int retries = 3;

        while(retries > 0){

            try {

                Product product = productRepository.findByProductId(productId)
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + productId));

                if (product.getStock() < quantity) {
                    throw new RuntimeException("Stock insuficiente");
                }

                product.setStock(product.getStock() - quantity);

                productRepository.save(product);

                return;


            }catch (OptimisticLockingFailureException ex){
                retries--;
                if(retries == 0){
                    throw new RuntimeException("Error de concurrencia, intenta de nuevo");
                }
            }

        }
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
    public ProductResponse getByProductId(Long productId) {
        Product product = productRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + productId));

        return ProductMapping.toDto(product);
    }

    @Override
    public ProductResponse createProduct(ProductRequest product) {
        Product p =productRepository.save(ProductMapping.toEntity(product));
        return ProductResponse.builder()
                .name(product.getName())
                .stock(product.getStock())
                .build();
    }
}