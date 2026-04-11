package com.practice.store.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDto {
    private Long idProducto;

    private String nombreProducto;

    private Double precio;
}
