package com.practice.store.mapper;

import com.practice.store.dto.request.CarritoRequestDto;
import com.practice.store.dto.response.CarritoResponseDto;
import com.practice.store.model.Carrito;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CarritoMapper {

    public static CarritoResponseDto toDto(Carrito carrito){
        return CarritoResponseDto.builder()
                .idCarrito(carrito.getIdCarrito())
                .productos(
                        carrito.getProductos().stream()
                                .map(CarritoProductoMapper::toDto) // convertir cada producto a su DTO
                                .collect(Collectors.toList())
                )
                .build();
    }


    public static Carrito toEntity(CarritoRequestDto carritoRequestDto){
        Carrito carrito = Carrito.builder()
                .productos(new ArrayList<>())
                .build();

        return carrito;
    }

}
