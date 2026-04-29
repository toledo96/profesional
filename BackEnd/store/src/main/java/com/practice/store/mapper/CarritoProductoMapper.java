package com.practice.store.mapper;

import com.practice.store.dto.request.CarritoProductoRequestDto;
import com.practice.store.dto.request.CarritoRequestDto;
import com.practice.store.dto.response.CarritoProductoResponseDto;
import com.practice.store.dto.response.CarritoResponseDto;
import com.practice.store.model.Carrito;
import com.practice.store.model.CarritoProducto;
import com.practice.store.model.Producto;

public class CarritoProductoMapper {

    public static CarritoProductoResponseDto toDto(CarritoProducto carritoProducto){
        CarritoProductoResponseDto productoResponseDto = CarritoProductoResponseDto.builder()
                .carritoProductoId(carritoProducto.getCarritoProductoId())
                .carritoId(carritoProducto.getCarrito().getIdCarrito())
                .nombreProducto(carritoProducto.getProducto().getNombreProducto())
                .productoId(carritoProducto.getProducto().getIdProducto())
                .precioUnitario(carritoProducto.getPrecioUnitario())
                .cantidad(carritoProducto.getCantidad())
                .build();

        return productoResponseDto;
    }


    public static CarritoProducto toEntity(CarritoProductoRequestDto dto) {
        Carrito carrito = Carrito.builder()
                .idCarrito(dto.getCarritoId())
                .build();

        Producto producto = Producto.builder()
                .idProducto(dto.getProductoId())
                .build();

        return CarritoProducto.builder()
                .carritoProductoId(dto.getCarritoId())
                .carrito(carrito)
                .producto(producto)
                .cantidad(dto.getCantidad())
                .build();
    }
}




