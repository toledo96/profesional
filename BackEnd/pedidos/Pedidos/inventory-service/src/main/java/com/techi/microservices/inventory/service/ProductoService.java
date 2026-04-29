package com.techi.microservices.inventory.service;


import com.techi.microservices.inventory.model.Product;

public interface ProductoService {

    void reduceStock(Long productId, Integer quantity);

    void increaseStock(Long productId, Integer quantity);

    Product getByProductId(Long productId);

    Product createProduct(Product product);

}
