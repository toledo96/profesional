package com.techi.microservices.inventory.repository;

import com.techi.microservices.inventory.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product,String> {
    Optional<Product> findByProductId(Long productId);
}
