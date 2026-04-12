package com.practice.store.dto.request;

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
public class CarritoRequestDto {
        /*
        - Request DTOs → sin ID (porque el cliente no lo define).
        - Response DTOs → con ID (porque el servidor lo devuelve al cliente).

    */


    private List<CarritoProductoRequestDto> productos = new ArrayList<>();

}
