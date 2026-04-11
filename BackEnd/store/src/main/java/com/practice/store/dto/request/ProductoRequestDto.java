package com.practice.store.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequestDto {

    private String nombreProducto;

    private Double precio;

    private Integer cantidadStock;

}
