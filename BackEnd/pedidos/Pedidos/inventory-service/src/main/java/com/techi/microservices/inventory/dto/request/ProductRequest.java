package com.techi.microservices.inventory.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
    private Long productId;

    private String name;

    private Integer stock;
}
