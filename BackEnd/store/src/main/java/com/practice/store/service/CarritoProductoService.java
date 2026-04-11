package com.practice.store.service;

import com.practice.store.dto.response.CarritoProductoResponseDto;

import java.util.List;

public interface CarritoProductoService {

    CarritoProductoResponseDto agregarProducto(Long carritoId, Long productoId, Integer cantidad);

    CarritoProductoResponseDto actualizarCantidad(Long carritoProductoId, Integer nuevaCantidad);

    void eliminarProductoDelCarrito(Long carritoProductoId);

    List<CarritoProductoResponseDto> obtenerProductosDeCarrito(Long carritoId);

    Double calcularTotalCarrito(Long carritoId);



}
