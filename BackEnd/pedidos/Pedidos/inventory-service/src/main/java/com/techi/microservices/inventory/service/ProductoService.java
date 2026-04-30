package com.techi.microservices.inventory.service;


import com.techi.microservices.inventory.dto.request.ProductRequest;
import com.techi.microservices.inventory.dto.response.ProductResponse;
import com.techi.microservices.inventory.model.Product;

public interface ProductoService {

    void reduceStock(Long productId, Integer quantity);

    void increaseStock(Long productId, Integer quantity);

    ProductResponse getByProductId(Long productId);

    ProductResponse createProduct(ProductRequest product);

}
