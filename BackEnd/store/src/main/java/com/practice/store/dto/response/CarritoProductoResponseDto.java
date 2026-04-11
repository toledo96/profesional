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
    private Long carritoProductoId; // ID generado por la BD
    private Long carritoId;
    private Long productoId;
    private String nombreProducto;
    private Double precioUnitario;
    private Integer cantidad;


}
