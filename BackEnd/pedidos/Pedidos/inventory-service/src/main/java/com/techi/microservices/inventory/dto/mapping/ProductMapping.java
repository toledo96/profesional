package com.techi.microservices.inventory.dto.mapping;

import com.techi.microservices.inventory.dto.request.ProductRequest;
import com.techi.microservices.inventory.dto.response.ProductResponse;
import com.techi.microservices.inventory.model.Product;

public class ProductMapping {

    public static ProductResponse toDto(Product product){
        return ProductResponse.builder()
                .name(product.getName())
                .stock(product.getStock())
                .build();
    }

    public static Product toEntity(ProductRequest product){
        return Product.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .stock(product.getStock())
                .build();
    }

}
