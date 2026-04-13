package com.practice.store.service;

import com.practice.store.dto.request.ProductoRequestDto;
import com.practice.store.dto.response.ProductoResponseDto;
import com.practice.store.model.Producto;

import java.util.List;

public interface ProductoService {

    /*
        - Request DTO → solo para entrada (crear/actualizar).
        - Response DTO → para salida (mostrar datos al cliente).
    */

    ProductoResponseDto crearProducto(ProductoRequestDto productoRequestDto,String requestId);

    ProductoResponseDto actualizarProducto(ProductoRequestDto productoRequestDto, Long productoId);

    List<ProductoResponseDto> obtenerProductos();

    ProductoResponseDto obtenerProducto(Long productoId);

    void eliminarProducto(Long productoId);
}

