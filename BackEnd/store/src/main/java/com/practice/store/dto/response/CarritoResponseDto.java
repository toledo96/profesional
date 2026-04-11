package com.practice.store.dto.response;

import com.practice.store.model.CarritoProducto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarritoResponseDto {

    /*
        - Request DTOs → sin ID (porque el cliente no lo define).
        - Response DTOs → con ID (porque el servidor lo devuelve al cliente).

    */

    private Long idCarrito;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL)
    private List<CarritoProductoResponseDto> productos = new ArrayList<>();
}
