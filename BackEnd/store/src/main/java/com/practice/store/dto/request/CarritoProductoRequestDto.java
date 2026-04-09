package com.practice.store.dto.request;

import com.practice.store.model.Carrito;
import com.practice.store.model.Producto;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarritoProductoRequestDto {

    private long carritoProductoId;

    private Integer cantidad;

    private Carrito carrito;

    private Producto producto;

}
