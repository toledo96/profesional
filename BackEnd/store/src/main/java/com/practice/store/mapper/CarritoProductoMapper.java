package com.practice.store.mapper;

import com.practice.store.dto.request.CarritoProductoRequestDto;
import com.practice.store.dto.request.CarritoRequestDto;
import com.practice.store.dto.response.CarritoProductoResponseDto;
import com.practice.store.dto.response.CarritoResponseDto;
import com.practice.store.model.CarritoProducto;

public class CarritoProductoMapper {

    public static CarritoProductoResponseDto toDto(CarritoProducto carritoProducto){
        CarritoProductoResponseDto productoResponseDto = CarritoProductoResponseDto.builder()
                .carritoProductoId(carritoProducto.getCarritoProductoId())
                .carritoId(carritoProducto.getCarrito().getIdCarrito())
                .nombreProducto(carritoProducto.getProducto().getNombreProducto())
                .productoId(carritoProducto.getProducto().getIdProducto())
                .precioUnitario(carritoProducto.getPrecioUnitario())
                .build();

        return productoResponseDto;
    }


    public static CarritoProducto toEntity(CarritoProductoRequestDto dto) {
        return CarritoProducto.builder()
                .cantidad(dto.getCantidad())
                .build();
    }



}
