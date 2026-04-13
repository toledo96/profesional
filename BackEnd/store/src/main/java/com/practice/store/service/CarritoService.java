package com.practice.store.service;

import com.practice.store.dto.request.CarritoRequestDto;
import com.practice.store.dto.response.CarritoResponseDto;

import java.util.List;

public interface CarritoService {

    CarritoResponseDto crearCarrito(CarritoRequestDto carritoRequestDto,String requestId);

    CarritoResponseDto actualizarCarrito(CarritoRequestDto carritoRequestDto, Long carritoId);

    List<CarritoResponseDto> obtenerCarritos();

    CarritoResponseDto obtenerCarrito(Long carritoId);

    void eliminarCarrito(Long carritoId);

}
