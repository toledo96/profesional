package com.practice.store.mapper;

import com.practice.store.dto.request.ProductoRequestDto;
import com.practice.store.dto.response.ProductoResponseDto;
import com.practice.store.model.Producto;

public class ProductoMapper {

    public static ProductoResponseDto toDto(Producto producto){
        ProductoResponseDto productoResponseDto = ProductoResponseDto.builder()
                .idProducto(producto.getIdProducto())
                .nombreProducto(producto.getNombreProducto())
                .precio(producto.getPrecio())
                .build();

        return productoResponseDto;
    }


    public static Producto toEntity(ProductoRequestDto productoRequestDto){
        Producto producto = Producto.builder()
                .nombreProducto(productoRequestDto.getNombreProducto())
                .precio(productoRequestDto.getPrecio())
                .cantidadStock(productoRequestDto.getCantidadStock())
                .build();

        return producto;
    }

}
