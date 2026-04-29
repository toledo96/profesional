package com.practice.store.controller;

import com.practice.store.dto.request.CarritoRequestDto;
import com.practice.store.dto.response.CarritoResponseDto;
import com.practice.store.service.CarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carritos")
@RequiredArgsConstructor
public class CarritoController {

    private final CarritoService carritoService;

    // Crear carrito con idempotencia
    @PostMapping
    public ResponseEntity<CarritoResponseDto> crearCarrito(
            @RequestBody CarritoRequestDto carritoRequestDto,
            @RequestHeader("X-Request-Id") String requestId) {

        CarritoResponseDto response = carritoService.crearCarrito(carritoRequestDto, requestId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Actualizar carrito
    @PutMapping("/{carritoId}")
    public ResponseEntity<CarritoResponseDto> actualizarCarrito(
            @PathVariable Long carritoId,
            @RequestBody CarritoRequestDto carritoRequestDto) {

        CarritoResponseDto response = carritoService.actualizarCarrito(carritoRequestDto, carritoId);
        return ResponseEntity.ok(response);
    }

    // Obtener todos los carritos
    @GetMapping
    public ResponseEntity<List<CarritoResponseDto>> obtenerCarritos() {
        List<CarritoResponseDto> carritos = carritoService.obtenerCarritos();
        return ResponseEntity.ok(carritos);
    }

    // Obtener carrito por ID
    @GetMapping("/{carritoId}")
    public ResponseEntity<CarritoResponseDto> obtenerCarrito(@PathVariable Long carritoId) {
        CarritoResponseDto response = carritoService.obtenerCarrito(carritoId);
        return ResponseEntity.ok(response);
    }

    // Eliminar carrito
    @DeleteMapping("/{carritoId}")
    public ResponseEntity<Void> eliminarCarrito(@PathVariable Long carritoId) {
        carritoService.eliminarCarrito(carritoId);
        return ResponseEntity.noContent().build();
    }
}