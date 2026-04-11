package com.practice.store.service.Impl;

import com.practice.store.dto.request.CarritoRequestDto;
import com.practice.store.dto.response.CarritoResponseDto;
import com.practice.store.repository.CarritoRepository;
import com.practice.store.service.CarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarritoServiceImpl implements CarritoService {

    private CarritoRepository carritoRepository;

    @Override
    public CarritoResponseDto crearCarrito(CarritoRequestDto carritoRequestDto) {
        return null;
    }

    @Override
    public CarritoResponseDto actualizarCarrito(CarritoRequestDto carritoRequestDto, Long carritoId) {
        return null;
    }

    @Override
    public List<CarritoResponseDto> obtenerCarritos() {
        return null;
    }

    @Override
    public CarritoResponseDto obtenerCarrito(Long carritoId) {
        return null;
    }

    @Override
    public void eliminarCarrito(Long carritoId) {

    }
}
