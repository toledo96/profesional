package com.techi.microservices.inventory.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

    private String name;

    private Integer stock;
}
