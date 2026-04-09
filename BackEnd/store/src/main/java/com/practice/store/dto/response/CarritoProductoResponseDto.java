package com.practice.store.dto.response;

import com.practice.store.model.Carrito;
import com.practice.store.model.Producto;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarritoProductoResponseDto {
    private long carritoProductoId;

    private Integer cantidad;

    private Carrito carrito;

    private Producto producto;
}
