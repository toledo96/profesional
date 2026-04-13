package com.practice.store.controller;

import com.practice.store.dto.response.CarritoProductoResponseDto;
import com.practice.store.service.CarritoProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carritos/{carritoId}/productos")
@RequiredArgsConstructor
public class CarritoProductoController {

    private final CarritoProductoService carritoProductoService;

    // Agregar producto al carrito con idempotencia
    @PostMapping("/{productoId}")
    public ResponseEntity<CarritoProductoResponseDto> agregarProducto(
            @PathVariable Long carritoId,
            @PathVariable Long productoId,
            @RequestParam Integer cantidad,
            @RequestHeader("X-Request-Id") String requestId) {

        CarritoProductoResponseDto response =
                carritoProductoService.agregarProducto(carritoId, productoId, cantidad, requestId);
        return ResponseEntity.ok(response);
    }

    // Actualizar cantidad de un producto en el carrito
    @PutMapping("/{carritoProductoId}")
    public ResponseEntity<CarritoProductoResponseDto> actualizarCantidad(
            @PathVariable Long carritoProductoId,
            @RequestParam Integer nuevaCantidad) {

        CarritoProductoResponseDto response =
                carritoProductoService.actualizarCantidad(carritoProductoId, nuevaCantidad);
        return ResponseEntity.ok(response);
    }

    // Obtener todos los productos de un carrito
    @GetMapping
    public ResponseEntity<List<CarritoProductoResponseDto>> obtenerProductosDeCarrito(
            @PathVariable Long carritoId) {

        List<CarritoProductoResponseDto> productos =
                carritoProductoService.obtenerProductosDeCarrito(carritoId);
        return ResponseEntity.ok(productos);
    }

    // Eliminar producto del carrito
    @DeleteMapping("/{carritoProductoId}")
    public ResponseEntity<Void> eliminarProductoDelCarrito(
            @PathVariable Long carritoProductoId) {

        carritoProductoService.eliminarProductoDelCarrito(carritoProductoId);
        return ResponseEntity.noContent().build();
    }

    // Calcular total del carrito
    @GetMapping("/total")
    public ResponseEntity<Double> calcularTotalCarrito(@PathVariable Long carritoId) {
        Double total = carritoProductoService.calcularTotalCarrito(carritoId);
        return ResponseEntity.ok(total);
    }
}
